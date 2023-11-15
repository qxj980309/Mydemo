package com.example.demo.mock.common.util;


import com.example.demo.mock.common.Constants.Constants;
import com.example.demo.mock.entity.po.CommonMessagePO;
import com.example.demo.mock.entity.po.ExcelPO;
import com.example.demo.mock.entity.po.InterfacePO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
* 校验请求param内容
* */
public class ValidatorUtils {
    private final static Logger log = LoggerFactory.getLogger(ValidatorUtils.class);

    /*
    * * 提取D (x1,x2)格式的X1 X2的正则表达式
    * */
    public static final Pattern PATTERND = Pattern.compile("D\\((\\d+), \\s*(\\d+)\\)");

    /*
    * 提取ANS格式前级
    * */
    public static final String PATTERNANS = "^([ANS]|AN|AS|ANS)(|\\.{2})[1-9]\\d*$";

    /*
    *  取数字
    * */
    public static final Pattern PATTERNNUM = Pattern.compile("\\d+");

    /*
    *  校验请求参敬
    *  @param bodyMap 请求参数
    *  @param interfaceP0 接口信息
    *  @param commonMessageP0 公共报文信息
    *  @return 校验结果
    * */
    public static String checkParam(Map<String , Object> bodyMap, InterfacePO interfacePO, CommonMessagePO commonMessagePO){
        ExcelPO requestBody = interfacePO.getRequestBody();
        StringBuilder errMsg = new StringBuilder();
        if (requestBody != null) {
            List<Map<String, String>> bodyList = requestBody.getBodyList();
            if (CollectionUtils.isEmpty(bodyList) || bodyList.size() == 0){
                log.info("没有校验规则");
                return "";
            }
            ExcelPO checkAll = requestBody;
            //判断是否关联公共报文，若关联，合并后再进行参数校验
            if (commonMessagePO != null) {
                checkAll = MergeCommonMessageUtil.mergeCommon(interfacePO, commonMessagePO);
            }
            List<TreeNode> treeNodes = listToTree(checkAll);
            checkNode(treeNodes,bodyMap,errMsg);
        }
        return errMsg.toString();
    }


    /*
    * 判定每个字段的格式
    * @param treeNodes 所有宁段树
    * @param bodyMap 请求param
    * @param errMsg 错误信息
    * */
    public static void checkNode(List<TreeNode> treeNodes, Map<String, Object> bodyMap, StringBuilder errMsg) {
        if (CollectionUtils.isEmpty(treeNodes)) {
            return;
        }
        for (TreeNode node : treeNodes) {
            checkNode(bodyMap , node, errMsg);
        }
    }

    /*
     * 判定每个宁段的格式
     * @param treeNodes 所有宁段树
     * @param bodyMap 请求param
     * @param errMsg 错误信息
     * */
    public static void checkNode(Map<String ,Object> bodyMap, TreeNode node, StringBuilder errMsg) {
        //先判断当前节点是否是子节点，如果是子节点判断具体的param
        if (node.children.size() != 0) {
            log.info("当前节点为父节点");
            List<TreeNode> children = node.getChildren();
            for (TreeNode child : children) {
                checkNode(bodyMap,child, errMsg);
            }
            return;
        }

        /*
        * t判断具体param
        * 1.先判断是否字段必输，如果请求param必输字段为空，抛出异常
        * 2.然后根据格式判定是那种格式，主要分为ANS类、D(x1，x2》和日期
        * 2.1 如果是ANS类，需要判断是否是定长还是边长，确定具体的Patterr
        * 2.2 如果是D(x1,x2)类，需要提取出具体长度，获取Pattern
        * 2.3日期根据报文格式获取正则表达式3.根据正则表达式与输入数据匹配，如不符合录入errMsg
        * */
        //获取请求param的值
        String value = JSONUtil.getValue(bodyMap, node.getName());
        //取必输字段
        if (Constants.Y.equals(node.getRequired())) {
            if (StringUtils.isBlank(value)) {
                log.info("param {} required! ", node.getName());
                errMsg.append("param ").append(node.getName()).append(" required").append("/");
            }
        }
        //判断格式,如果输入有值
        if (StringUtils.isNotBlank(value)) {
            //取格式字段长度字段
            String format = node.getFormat();
            if (StringUtils.isNotBlank(format) && RegexUtil.matchesANS(format)) {
                //A N S AN AS ANS
                //是否包含”。.”
                String num = getNum(format);
                if ("ANS".equals(getAnsHeader(format)) || "AS".equals(getAnsHeader(format)) || "S".equals(getAnsHeader(format))) {
                    checkS(format, num, value, node, errMsg);
                } else {
                    String pattern = getPattern(format, num);
                    matchPattern(pattern, value, node, errMsg);
                }
            } else if (StringUtils.isNotBlank(format) && RegexUtil.matchesD(format)){
                //D(x1,x2)
                String[] group = getD(format);
                if (group == null) {
                    errMsg.append("param ").append(node.getName()).append(" not match D(x1,x2)").append("/");
                }else {
                    String patternD = getPattenDx(group[0], group[1]);
                    matchPattern(patternD, value, node, errMsg);
                }
            } else if (StringUtils.isNotBlank(format) & DateUtil.isRightPattern(format)){
                log.info("param{}日期类格式不匹配",node.getName());
                String patternDate = validateDate(node .getFormat());
                matchPattern(patternDate, value, node, errMsg);
            }else {
                log.info("param}没有匹配格式",node.getName());
                errMsg.append("param ").append(node.getName()).append(" has no match format").append("/");
            }
        }

    }

    /*
    * * 断S、AS、ANS儿种情况
     * 因为S 包含中文，所以通过排除法判断
    * @param format 格式《除开数字)
    * @param num 格式中长度
    * @param value 输入内容
    * @param node 当前判断节点
    * @param errMsg 份误信息
    *
    * */
    private static void checkS(String format, String num, String value, TreeNode node, StringBuilder errMsg) {
        String pattern = "[a-ZA-Z0-9]+$";
        String patternN = "[0-9]+$";
        //判断是否是变长
        if (format.contains(Constants.UNCERTAINLENGTH)) {
            switch (getAnsHeader(format)) {
                case Constants.S:
                    //判断没有字母和数字
                    checkMatch(value, node, errMsg,pattern);
                    checkVariableLength(num, value, node, errMsg);
                    break;
                case Constants.AS:
                    //判断没有数字
                    checkMatch(value, node, errMsg, patternN);
                    checkVariableLength(num, value, node, errMsg);
                    break;
                case Constants.ANS:
                    //判断长度
                    checkVariableLength(num, value, node, errMsg);
                    break;
                default:
                    break;
            }
        } else {
            switch (getAnsHeader(format)) {
                case Constants.S:
                    //判断没有字母和数字
                    checkMatch(value, node, errMsg, pattern);
                    checkLength(num,value, node, errMsg);
                    break;
                case Constants.AS:
                    //判断没有数字
                    checkMatch(value, node, errMsg, patternN);
                    checkLength(num,value, node, errMsg);
                    break;
                case Constants.ANS:
                    //判断长度
                    checkLength(num, value, node, errMsg);
                    break;
                default:
                    break;
            }
        }

    }

    /*
    * 判断当前格式长度是否符合定长标准
    *  @param num 格式中长度
    *  @param valve 输入内容
    *  @param node 当前判断节点
    *  @param errMsg 误信息
    * */
    private static void checkLength(String num, String value, TreeNode node, StringBuilder errMsg) {
        if (value.length() != Integer.parseInt(num)) {
            errMsg.append("param ").append(node.getName()).append(" not match length")
                    .append(node.getFormat()).append("/");
        }
    }

    /*
     *当前格式长度是否符合变长标准
     * @param num格式中长度
     * @param 输入内容value
     * @param node 当前判断节点
     * @param errMsg 份误信息
    **/
    private static void checkVariableLength(String num, String value, TreeNode node, StringBuilder errMsg) {
        if (value.length() > Integer.parseInt(num)) {
            errMsg.append("param ").append(node.getName()).append(" not match length")
                    .append(node.getFormat()).append("/ ");
        }
    }

    /*
    * 排除反例情况
    * @param valve输入内容
    * @param node 当前判断节点
    * @param errMsg错误信息
    * @param pattern 正则表达式
    * */
    private static void checkMatch(String value, TreeNode node, StringBuilder errMsg, String pattern) {
        boolean matches;
        matches = Pattern.matches(pattern, value);
        if (matches) {
            errMsg.append("param ").append(node.getName()).append(" not match format")
                    .append(node.getFormat()).append(" ");
        }
    }

    /*
    * 1.Y:当天所在网属于的年份;y: 年份 2,M: 月份; : 分数 3.D: 年份中的天数; : 份中的天数
    * 4.H 天中的小时数(0-23); h; 一天中的小时影(1-12)
    * 5.S:毫秒数;S:秒数 6.W; 月份中的周数; w: 年中的周数(未考，未知格式) 7.Z、Z: 时区(考UTC偏移量表示方式)
    * 构建日期类的正则表达式，用于判定当前输入是否符合录入格式
    *
    *  @param doteFormat 入格式
    *  @return 格式判断结果
    * */
    public static String validateDate(String dateFormat) {
        String regex = dateFormat.replaceAll("yyyy", "(\\\\d{4})") .replaceAll( "YYYY","(\\\\d{4})")
                .replaceAll("MM","([0-1]\\\\d)")
                .replaceAll("DD","([0-3]\\\\d)")
                .replaceAll("dd","([0-3]\\\\d)")
                .replaceAll("mm","([0-5]\\\\d)")
                .replaceAll("HH","([0-5]\\\\d)")
                .replaceAll("hh","(0[1-9]|1[0-2])")
                .replaceAll("ss","([0-5]\\\\d)")
                .replaceAll("SSS","(\\\\d{3})")
                .replaceAll("WW","(W\\\\d{2}")
                .replaceAll("SSS","(\\\\d{3}")
                .replaceAll("Z","([+-]\\\\d{1,2}:\\\\d{2})")
                .replaceAll("z","([+-]\\\\d{1,2}:\\\\d{2})")
                .replaceAll(" ","\\\\s");
        regex = "a" + regex + "$";
        return regex;
    }

    /*
    * 根据格式的正则表达式与请求param内容进行匹应
    *  @param pattern 正则表达式
    *  @param value请求param值
    *  @param node当前节点
    *  @param errMsg错误信息
    * */
    public static void matchPattern(String pattern, String value, TreeNode node,StringBuilder errMsg){
        if (StringUtils.isNotBlank(pattern)) {
            boolean matches = Pattern .matches(pattern, value);
            if (!matches) {
                log.info("param与输入格式不匹配",node.getName(),node.getFormat());
                errMsg.append("param ").append(node .getName()) .append(" not match format")
                        .append(node .getFormat()) .append("/");
            } else {
                log.info("param没有匹配格式",node.getName());
                errMsg.append("param ").append(node.getName ()) .append(" has no match format")
                        .append("/");
            }
        }
    }
    static class TreeNode{
        String id;
        String name;
        String format;
        String required;
        List<TreeNode> children = new ArrayList<>();
        public TreeNode(String id, String name, String format, String required) {
            this.id = id;
            this.name = name;
            this.format = format;
            this.required = required;
        }

        public List<TreeNode> getChildren() { return this.children; }

        public String getId() { return id; }

        public void setId(String id) { this.id = id; }

        public String getName() { return name; }

        public void setName(String name) { this.name = name; }

        public String getFormat() { return format;}

        public String getRequired() { return required; }
    }

    /*
    * 将bodylistparqn转为treenode，使于层级判断
    *
    * @param requestBody 请param
    *  @return treeNode树
    * */
    public static List<TreeNode> listToTree(ExcelPO requestBody) {
        Map<String,String> header = requestBody.getHeaders();
        List<Map<String,String>> bodyList = requestBody.getBodyList();
        if (CollectionUtils.isEmpty(bodyList) && header.isEmpty()) {
            return null;
        }
        //遍历当前bodyList,转为List<TreeNode>
        Map<String,TreeNode> map = new HashMap<>(bodyList.size());
        List<TreeNode> treeList = new ArrayList<>();
        String idKey = header.get("序号");
        String nameKey = header.get("英文名称");
        String formatKey = header.get("格式长度");
        String required = header.get("是否必填");

        for (Map<String, String> body : bodyList){
            String id = body.get(idKey);
            if (!id.contains(".")){
                TreeNode treeNode = new TreeNode(body.get(idKey), body.get(nameKey),
                        body.get(formatKey),body.get(required));
                treeList.add(treeNode);
                map.put(id,treeNode);
            }  else {
                String parentId = id.substring(0, id.lastIndexOf( "."));
                TreeNode treeNode = new TreeNode(id, map.get(parentId).name +""+ body.get(nameKey),
                        body.get(formatKey), body.get(required));
                map.get(parentId).children.add(treeNode);
                map.put(id,treeNode);
            }
        }
        return treeList;
    }

    /*
    * 根据报文格式取具体的正则表达式
    * * @param format 报文格式
     * @param num报文格式长度
     *  @return 正则表达式
    * */
    public static String getPattern(String format, String num) {
        //判断是否是变长
        if (format.contains(Constants.UNCERTAINLENGTH)){
            switch (getAnsHeader(format)) {
                case Constants.A:
                    return getPattenAmx(num);
                case Constants.N:
                    return getPattenNMx(num);
                case Constants.AN:
                    return getPattenANMx(num);
                default:
                    log.info("格式不符合");
                    return "";
            }
        } else {
            switch (getAnsHeader(format)) {
                case Constants.A:
                    return getPattenAmx(num);
                case Constants.N:
                    return getPattenNMx(num);
                case Constants.AN:
                    return getPattenANMx(num);
                default:
                    log.info("格式不符合");
                    return "";
            }
        }
    }

    /*
    * 获取ANS类的前缀宁母
    *
    * @param format 报文格式
    * @return 前级字母
    * */
    public static String getAnsHeader(String format) {
        Pattern patterns = Pattern.compile(PATTERNANS);
        Matcher matcher = patterns .matcher(format);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    /*
     * 获取ANS类的前缀宁母
     *
     * @param format 报文格式
     * @return 格式长度
     * */
    public static String getNum(String format){
        Matcher matcher = PATTERNNUM.matcher(format);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    /*
    * 获取D (X1，X2)格式的X1、X2
    *
    * @param format 报文格式
    * @return x1，x2
    * */
    public static String[] getD(String format) {
        Matcher matcher = PATTERND.matcher(format);
        String group1;
        String group2;
        if (matcher.find()){
            group1 = matcher.group(1);
            group2 = matcher.group(2);
            return new String[]{group1, group2};
        } else {
            log.info("D(x1,x2)格式不正确，无法提取数字");
            return null;
        }
    }

    /*
    *  1.Ax: X位定长的字母宁符
    * *@param Length 定长长度
    * @return 正则表达式
    * */
    public static String getPattenAx(String length) { return "^[a-zA-Z](" + length + "}$";}

    /*
     *   2.A..x;最大长度为X位的变长宁母宁符
     *  @param maxLength 最大长度
     * @return 正则表达式
     * */
    public static String getPattenAmx(String maxlength) { return  "^[a-ZA-Z]{1," + maxlength + "}$"; }

    /*
     *  3.ANX;X位定长的字母和/或数宁字符
     *  @param maxLength 最大长度
     * @return 正则表达式
     * */
    public static String getPattenANx(String length) { return  "^[a-ZA-Z0-9](" + length + ")$"; }

    /*
     *   4.AN..X; 最大长度为X位的变长宁母和/或数字宁符
     *  @param maxLength 最大长度
     * @return 正则表达式
     * */
    public static String getPattenANMx(String maxLength) { return "a[a-ZA-Z-9](1," + maxLength + ")$"; }

    /*
     *  9.Nx:X位定长的整型数值，右，首位有效数宁前充零。若表示金颜，则最右二位为角分
     *  @param maxLength 最大长度
     * @return 正则表达式
     * */
    public static String getPattenNx(String length) {
        //暂不支持金额
        return "^[o-9]{"+ length + "}$";
    }

    /*
     *  10.N..X; 最大长度为X位的整型数值。若表示金顾，则最右二位为角分
     *  @param maxLength 最大长度
     * @return 正则表达式
     * */
    public static String getPattenNMx(String maxlength) {
        //暂不支持金额
        return "^[0-9]{1," + maxlength + "}$";
    }

    /*
     *  13.D(x1,x2):最大长度为X1位的数，其中小数长度为x2位，小数点不占位。
     *  @param x1 最大长度
     * @param x2 最大长度
     * @return 正则表达式
     * */
    public static String getPattenDx(String x1, String x2) {
        return String.format(" \\d(\\,%d}(\\.\\d(%d})?", Integer.parseInt(x1) - Integer.parseInt(x2),
                Integer.parseInt(x2));
    }
}
