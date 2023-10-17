package com.example.demo.service;

import com.example.demo.mock.entity.vo.SearchVo;
import com.example.demo.mock.service.DataService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class DataServiceImplTest {
    @Resource
    private DataService dataService;
    @Test
    void sys() {
        Integer pageIndex = 10;
        Integer pageSize = 1;
        SearchVo searchVo = new SearchVo("123","321","",
                        "","01","2023-09-10-2023-10-13");
        dataService.selectSys(searchVo, pageIndex, pageSize);
    }



    //    private Date getYearFirstDay(){
//        Date currentDate = new Date();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(currentDate);
//        calendar.set(Calendar.MONTH, 0); // 设置月份为1月
//        calendar.set(Calendar.DAY_OF_MONTH, 1); // 设置日期为1号
//        return calendar.getTime();
//    }


//    private void deal(SearchVo searchVo, String dimension) {
//        if ("01".equals(dimension)){
//            startDate =getDate(searchVo.getDate().substring(0,4),pattern);
////            endDate = getDate(searchVo.getDate().substring(11,searchVo.getDate().length()-1),pattern);
//        } else if ("02".equals(dimension)){
//            startDate = getDate(searchVo.getDate().substring(0,7),pattern);
////            endDate = getDate(searchVo.getDate().substring(11,searchVo.getDate().length()-1),pattern);
//        } else {
//            startDate = getDate(searchVo.getDate().substring(0,10),pattern);
////            endDate = getDate(searchVo.getDate().substring(11,searchVo.getDate().length()-1),pattern);
//        }
//        endDate = getDate(searchVo.getDate().substring(11,searchVo.getDate().length()),pattern);
//    }

//        if (StringUtils.isNotEmpty(searchVo.getEndDate()) && StringUtils.isNotEmpty(searchVo.getStartDate())) {
//        statDate = getDate(searchVo.getStartDate(),pattern);
//        endDate = getDate(searchVo.getEndDate(),pattern);
//        wrapper.between("date", statDate, endDate);
//    } else {
//        switch (dimension) {
//            case "年":
//                if (StringUtils.isEmpty(searchVo.getStartDate())) {
//                    statDate = getYearFirstDay();
//                }
//                if (StringUtils.isEmpty(searchVo.getEndDate())) {
//
//                }
//        }
//    }



}
