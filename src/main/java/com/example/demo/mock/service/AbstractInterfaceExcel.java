package com.example.demo.mock.service;

import com.example.demo.myself.common.exception.BizException;
import com.example.demo.mock.common.enums.IsNeededEnum;
import com.example.demo.mock.common.util.DateUtil;
import com.example.demo.mock.common.util.RegexUtil;
import com.example.demo.mock.entity.po.ExcelPO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AbstractInterfaceExcel<T> implements InterfaceExcelService<T> {
    // 表头信息
    private final Map<String, T> headers = new HashMap<>();

    // 数据
    private final List<Map<T, String>> bodyList = new ArrayList<>();

    // 本块错误信息
    private final StringBuilder errorMsg = new StringBuilder();

    // 本行错误信息
    protected final StringBuilder currentBuilder = new StringBuilder();

    /*
    *用于存储属性名及层级，判断同层级是否有相同参数名
    * */
    private final Map<String, List<String>> nameMap = new HashMap<>();

    private boolean isIndexError = false;

    private final String ROOT = "root";

    private final Map<String, Integer> indexMap = new HashMap<String, Integer>() {{
        put(ROOT,0);
    }};

    private static final Set<String> NEED_HEADERS = new HashSet<String>() {{
        add("序号");
        add("英文名称");
        add("中文名称");
        add("格式长度");
        add("数类型");
        add("是否必填");
    }};

    private static final Set<String> NEED_BODIES = new HashSet<String>() {{
        add("序号");
        add("英文名称");
        add("中文名称");
        add("是否必填");
    }};

    private static final Set<String> TYPES = Stream.of( "char", "double","short","float","string",
            "boolean").collect(Collectors.toSet());

    /*
     * 其他需要校验的信息 将错误信息添加到currentBuilder中
     *
     *  @param data 本行数据
     * */
    //    public abstract void checkothers(Map<T, string> data) ;
    public void checkOthers(Map<T, String> data) {
    }

    @Override
    public void setHeaders(Map<String, T> map){ this.headers.putAll(map);}

    @Override
    public ExcelPO getHeaderAndBody() { return new ExcelPO(this.getHeaders(), this.getBodyList()); }

    @Override
    public Map<String, String> getHeaders() {
        HashMap<String, String> ret = new HashMap<>();
        for (Map.Entry<String, T> entry : headers.entrySet()) {
            ret.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return ret;
    }

    @Override
    public List<Map<String, String>> getBodyList() {
        ArrayList<Map<String, String>> ret = new ArrayList<>();
        for (Map<T, String> body : bodyList) {
            HashMap<String, String> map = new HashMap<>();
            for (Map.Entry<T, String> entry : body.entrySet()) {
                map.put(String.valueOf(entry.getKey()), entry.getValue());
            }
            ret.add(map);
        }
        return ret;
    }

    @Override
    public StringBuilder getErrorMsg() { return errorMsg; }

    @Override
    public void addRow(Map<T, String> data, int row) {
        // 校验必输列
        checkMustColumn(data);
        // 校验序号
        checkIndex(data);
        //校验数据类型
        checkType(data);
        //校验格式长度
        checkRegexAndLength(data);
        // 校验是否必填
        checkIsNeeded(data);
        // 验个性列《请求和响应不一致的列)
        checkOthers(data);
        //处理本行错误信息
        handleCurrentBuilder(row);
        //检验同层级是否有相同参教名
        checkParams(data);
        bodyList.add(data);
    }

    private void checkParams(Map<T, String> data) {
        T id = this.headers.get("序号");
        T name = this.headers.get("英文名称");
        String dataName = data.get(name);
        String dataId = data.get(id);
        List<String> idExists = nameMap.get(dataName);
        if (CollectionUtils.isEmpty(idExists)) {
            nameMap.put(dataName, new ArrayList<>(Collections.singletonList(dataId)));
            return;
        }

        // 判断当前序号是否与idExists中相同层级的序号相同
        Boolean flag = false;
        for (String idExist : idExists) {
            if (getLevel(idExist).equals(getLevel(dataId))) {
                errorMsg.append("相同层级下席号").append(dataId).append("与序号").append(idExist).append("的英文名称不能相同;");
                flag = false;
            }
        }
        if(!flag){
            idExists.add(dataId);
        }
    }

    @Override
    public void addHeader(Map<T, String> data, int row) {
        // 校验表头自身信息是否正确
        for (Map.Entry<T, String> entry : data.entrySet()) {
            if (StringUtils.isBlank(entry.getValue())){
                currentBuilder.append("表头不能为空,");
            }
            if (null != headers.get(entry.getValue())){
                currentBuilder.append("表头存在重复列").append(entry.getValue()).append(",");
            }
            headers.put(entry.getValue(), entry.getKey());
        }

        // 校验表头至少包含某些列
        Collection<String> values = data.values();
        for (String needHeader : NEED_HEADERS) {
            if (!values.contains(needHeader)) {
                currentBuilder.append("表头需要包含").append(needHeader).append(",");
            }
        }

        handleCurrentBuilder(row);
        // 表头存在错误信息时，不再处理该块数据
        if (errorMsg.length() > 0) {
            String s = errorMsg.toString();
            this.clear();
            throw new BizException(s);
        }
    }

    private void clear() {
        headers.clear();
        bodyList.clear();
        errorMsg.delete(0, errorMsg.length());
        indexMap.clear();
        nameMap.clear();
        indexMap.put(ROOT, 0);
    }

    private void checkMustColumn(Map<T, String> data) {
        for (String header : NEED_BODIES) {
            if (StringUtils.isBlank(data.get(headers.get(header)))){
                currentBuilder.append(header).append("不能为空,");
            }
        }
    }

    private void checkIndex(Map<T, String> data) {
        // 序号存在错误后不再校验
        if (isIndexError) {
            return;
        }
        String indexStr = data.get(headers.get("序号"));
        if (StringUtils.isBlank(indexStr)) {
            this.isIndexError = true;
            return;
        }
        int i = indexStr.lastIndexOf( ".");
        // 较取本层级的key
        String currentKey = -1 == i ? ROOT : indexStr.substring(0, i);
        Integer currentLayer = indexMap.get(currentKey);
        String index = indexStr.substring( i + 1);
        // 校验当前层级序号是否符合规则
        if (null == currentLayer || !String.valueOf(currentLayer + 1).equals(index)) {
            currentBuilder.append("序号不符合规则,");
            this.isIndexError = true;
        } else {
            // 合法数据，当前层级数值加1
            indexMap.put(currentKey, currentLayer + 1);
            // 初始化当前层级的子集
            indexMap.put(indexStr, 0);
        }
    }

    private void checkType(Map<T, String> data) {
        T columnIndex = headers.get("数据类型");
        if (StringUtils.isBlank(data.get(columnIndex))) {
            return;
        }
        String type = data.get(columnIndex).toLowerCase();
        if (isTypeIllegal(type)) {
            currentBuilder.append("数据类型不符合规则,");
        }else {
            // 更新为小写字母
            data.put(columnIndex, type);
        }
    }

    private void checkRegexAndLength(Map<T, String> data) {
        T columnIndex = headers.get("“格式长度");
        if (StringUtils.isBlank(data.get(columnIndex))) {
            return;
        }
        String pattern = data.get(columnIndex);
        if (!DateUtil.isRightPattern(pattern) && !RegexUtil.matchesANS(pattern) && !RegexUtil.matchesD(pattern)) {
            currentBuilder.append("格式长度不符合规则,");
        }
    }

    private void checkIsNeeded(Map<T, String> data) {
        T columnIndex = headers.get("是否必填");
        if (StringUtils.isBlank(data.get(columnIndex))){
            return;
        }
        String isMust = IsNeededEnum.toCode(data.get(columnIndex));
        if (IsNeededEnum.isOverRange(isMust)){
            currentBuilder.append("是否必填不符合规则,");
        } else {
            // 更新为code: Y N Cn
            data.put(columnIndex, isMust);
        }
    }

    private void handleCurrentBuilder(int row) {
        if (currentBuilder.length() > 0) {
            currentBuilder.insert( 0, "第" + row +"行:");
            errorMsg.append(replaceDelimiter(currentBuilder));
            currentBuilder.delete(0, currentBuilder.length());
        }
    }

    private StringBuilder replaceDelimiter(StringBuilder currentBuilder) {
        return currentBuilder.deleteCharAt(currentBuilder.length() - 1).append("; ");
    }

    private boolean isTypeIllegal(String type) {
        return !TYPES.contains(type);
    }

    private Object getLevel(String idExist) {
        if (!idExist.contains(".")) {
            return "root";
        }
        return idExist.substring(0, idExist.lastIndexOf( "."));
    }
}




