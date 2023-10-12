package com.example.demo.service.iml;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.DataPo;
import com.example.demo.entity.vo.DataVo;
import com.example.demo.entity.vo.SearchVo;
import com.example.demo.mapper.DataServiceMapper;
import com.example.demo.service.DataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



@Service
@Transactional
public class DataServiceImpl implements DataService {

    @Resource
    private DataServiceMapper dataServiceMapper;

    private Date statDate = null, endDate = null;
    private String pattern = "yyyy-MM-dd";

    @Override
    public IPage<DataVo> selectApi(SearchVo searchVo, Integer pageIndex, Integer pageSize) {
        return null;
    }

    @Override
    public IPage<DataVo> selectSys(SearchVo searchVo, Integer pageIndex, Integer pageSize) {
        DataPo dataPo = new DataPo();
        QueryWrapper<DataPo> wrapper = new QueryWrapper<>();
        wrapper.eq("send_sys_code", searchVo.getSendSysCode());
        wrapper.eq("acc_sys_code", searchVo.getAccSysCode());

        String dimension = searchVo.getDimension();
        //1 判断时间是否都有值
        if (StringUtils.isNotEmpty(searchVo.getEndDate()) && StringUtils.isNotEmpty(searchVo.getStartDate())) {
            statDate = getDate(searchVo.getStartDate(),pattern);
            endDate = getDate(searchVo.getEndDate(),pattern);
            wrapper.between("date", statDate, endDate);
        } else {
            switch (dimension) {
                case "年":
                    if (StringUtils.isEmpty(searchVo.getStartDate())) {
                        statDate = getYearFirstDay();
                    }
                    if (StringUtils.isEmpty(searchVo.getEndDate())) {

                    }
            }
        }
        Page<DataVo> dataVoPage = new Page<>();
        //2 获取数据库中数据
        Page<DataPo> dataPoPage = dataServiceMapper.selectPage(new Page<>(pageIndex, pageSize), wrapper);
        List<DataPo> records = dataPoPage.getRecords();
        ArrayList<DataVo> dataVos = new ArrayList<>();
        //3 转换类型
        for (DataPo record : records) {
            dataVos.add(new DataVo(record.getId(), record.getProjectId(), record.getSendSysCode(), record.getAccSysCode(), record.getName()
                    , record.getTxCode(), record.getDate().toString(), searchVo.getDimension(), record.getCount()));
        }
        buildResultPage(dataVoPage, dataPoPage, dataVos);
        return dataVoPage;
    }

    private void buildResultPage(Page<DataVo> dataVoPage, Page<DataPo> dataPoPage, ArrayList<DataVo> dataVos) {
        dataVoPage.setRecords(dataVos);
        dataVoPage.setCurrent(dataPoPage.getCurrent());
        dataVoPage.setSize(dataPoPage.getSize());
        dataVoPage.setTotal(dataPoPage.getTotal());
        dataVoPage.setPages(dataPoPage.getPages());
    }


    private Date getYearFirstDay(){
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.set(Calendar.MONTH, 0); // 设置月份为1月
        calendar.set(Calendar.DAY_OF_MONTH, 1); // 设置日期为1号
        return calendar.getTime();
    }

    private Date getDate(String dateString, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date parse = null;
        try {
            parse = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }
}
