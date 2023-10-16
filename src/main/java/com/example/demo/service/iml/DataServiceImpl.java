package com.example.demo.service.iml;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.DataPo;
import com.example.demo.entity.vo.DataVo;
import com.example.demo.entity.vo.SearchVo;
import com.example.demo.mapper.DataServiceMapper;
import com.example.demo.service.DataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class DataServiceImpl implements DataService {

    @Resource
    private DataServiceMapper dataServiceMapper;

    private String pattern = "yyyy-MM-dd";

    @Override
    public IPage<DataVo> selectApi(SearchVo searchVo, Integer pageIndex, Integer pageSize) {
        return null;
    }

    @Override
    public IPage<DataVo> selectSys(SearchVo searchVo, Integer pageIndex, Integer pageSize) {
        QueryWrapper<DataPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(searchVo.getSendSysCode()), "send_sys_code", searchVo.getSendSysCode());
        queryWrapper.eq(StringUtils.isNotEmpty(searchVo.getAccSysCode()), "acc_sys_code", searchVo.getAccSysCode());
        String dimension = searchVo.getDimension();
        Page<DataPo> dataPoPage;
        if (StringUtils.isNotEmpty(dimension)){
            Date startDate = getDate(searchVo, 0) , endDate = getDate(searchVo, 1);
            //1 判断时间维度是哪种类型
            switch (dimension) {
                //年
                case "01":
                    queryWrapper.select("project_id,send_sys_code,tx_code,acc_sys_code,name, SUM(count) as count");
                    queryWrapper.between(StringUtils.isNotEmpty(searchVo.getDimension()), "date", startDate, endDate);
                    queryWrapper.groupBy("project_id", "send_sys_code", "tx_code", "acc_sys_code", "name");
                    dataPoPage = dataServiceMapper.selectPage(new Page<>(pageIndex, pageSize), queryWrapper);
//                    dataPoPage = dataServiceMapper.selectData(new Page<>(pageIndex, pageSize), queryWrapper);
                    return buildData(searchVo, dataPoPage);
                //月
                case "02":
                    queryWrapper.select("project_id","send_sys_code,tx_code","acc_sys_code","name", "SUM(count) count","DATE_FORMAT(date,'%Y-%m') AS monthDate");
                    queryWrapper.between(StringUtils.isNotEmpty(searchVo.getDimension()), "date", startDate, endDate);
                    queryWrapper.groupBy("project_id", "send_sys_code", "tx_code", "acc_sys_code", "name", "DATE_FORMAT(date,'%Y-%m')");
                    dataPoPage = dataServiceMapper.selectPage(new Page<>(pageIndex, pageSize), queryWrapper);
                    return buildData(searchVo, dataPoPage);
                //日
                case "03":
                    queryWrapper.between(StringUtils.isNotEmpty(searchVo.getDimension()), "date", startDate, endDate);
                    dataPoPage = dataServiceMapper.selectPage(new Page<>(pageIndex, pageSize), queryWrapper);
                    return buildData(searchVo, dataPoPage);
            }
        } else {
            dataPoPage = dataServiceMapper.selectPage(new Page<>(pageIndex, pageSize), queryWrapper);
            return buildData(searchVo, dataPoPage);
        }
        return null;
    }


    /**
     * 获取数据
     *
     * @param searchVo
     * @return
     */
    private Page<DataVo> buildData(SearchVo searchVo, Page<DataPo> dataPoPage) {
        Page<DataVo> dataVoPage = new Page<>();
        List<DataVo> dataVos = getDataVos(searchVo, dataPoPage.getRecords());
        BeanUtils.copyProperties(dataPoPage, dataVoPage);
        dataVoPage.setRecords(dataVos);
        return dataVoPage;
    }


    private List<DataVo> getDataVos(SearchVo searchVo, List<DataPo> records) {
        List<DataVo> dataVos = new ArrayList<>();
        //3 转换类型
        //TODO date 需确认
        String date = null;
        if ("01".equals(searchVo.getDimension())) {
            date = searchVo.getDate();
        }
        for (DataPo record : records) {
            if ("02".equals(searchVo.getDimension())) {
                date = record.getMonthDate();
            }
            dataVos.add(new DataVo(record.getId(), record.getProjectId(), record.getSendSysCode(), record.getAccSysCode(), record.getName()
                    , record.getTxCode(), StringUtils.isNotEmpty(date) ? date : record.getDate().toString(), searchVo.getDimension(), record.getCount()));
        }
        return dataVos;
    }


    private Date getDate(SearchVo searchVo, int index) {
        String dateString = searchVo.getDate();
        String substring;
        if (index == 0) substring = dateString.substring(0, 10);
        else substring = dateString.substring(11);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(substring);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 获取日数据
     *
     * @param searchVo
     * @return
     */
//    private Page<DataVo> getDayData(SearchVo searchVo, Integer pageIndex, Integer pageSize) {
//
//        Page<DataPo>
//        Page<DataVo> dataVoPage = new Page<>();
//        BeanUtils.copyProperties(dataPoPage.getRecords(), dataVoPage);
//        return dataVoPage;
//    }


}
