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
        Date startDate = null, endDate = null;
        QueryWrapper<DataPo> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotEmpty(searchVo.getSendSysCode()),"send_sys_code",searchVo.getSendSysCode());
        wrapper.eq(StringUtils.isNotEmpty(searchVo.getAccSysCode()),"acc_sys_code",searchVo.getAccSysCode());
        String dimension = searchVo.getDimension();
        if (StringUtils.isNotEmpty(dimension)){
            //1 判断时间维度是哪种类型
            switch (dimension){
                case "01":
                    startDate =getFirstDate(searchVo.getDate().substring(0,4),pattern);
                    endDate = getDate(searchVo.getDate().substring(11,searchVo.getDate().length()),pattern);
                    wrapper.between("date",startDate,endDate);
                    break;
                case "02":
                    startDate =getFirstDate(searchVo.getDate().substring(0,7),pattern);
                    endDate = getDate(searchVo.getDate().substring(11,searchVo.getDate().length()),pattern);
                    wrapper.between("date",startDate,endDate);
                    break;
                case "03":
                    startDate =getDate(searchVo.getDate().substring(0,10),pattern);
                    endDate = getDate(searchVo.getDate().substring(11,searchVo.getDate().length()),pattern);
                    wrapper.between("date",startDate,endDate);
                    break;
            }
        }
        //2 获取数据库中数据
        Page<DataPo> dataPoPage = dataServiceMapper.selectPage(new Page<>(pageIndex, pageSize), wrapper);
        List<DataPo> records = dataPoPage.getRecords();
        ArrayList<DataVo> dataVos = new ArrayList<>();
        //3 转换类型
        for (DataPo record : records) {
            dataVos.add(new DataVo(record.getId(), record.getProjectId(), record.getSendSysCode(), record.getAccSysCode(), record.getName()
                    , record.getTxCode(), record.getDate().toString(), searchVo.getDimension(), record.getCount()));
        }
        Page<DataVo> dataVoPage = new Page<>();
//        BeanUtils.copyProperties(records,dataVoPage);
        buildResultPage(dataVoPage, dataPoPage, dataVos);
        return dataVoPage;
    }


    private Date getFirstDate(String substring, String pattern) {
        int year = Integer.parseInt(substring);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    private void buildResultPage(Page<DataVo> dataVoPage, Page<DataPo> dataPoPage, ArrayList<DataVo> dataVos) {
        dataVoPage.setRecords(dataVos);
        dataVoPage.setCurrent(dataPoPage.getCurrent());
        dataVoPage.setSize(dataPoPage.getSize());
        dataVoPage.setTotal(dataPoPage.getTotal());
        dataVoPage.setPages(dataPoPage.getPages());
    }

    private Date getDate(String dateString, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
