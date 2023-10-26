package com.example.demo.mock.controller;

import com.example.demo.common.result.Result;
import com.example.demo.mock.entity.vo.ProjectCountVo;
import com.example.demo.mock.service.HomeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Resource
    private HomeService homeService;

    @GetMapping("/allCount")
    public Result allCount() {
        try {
            ProjectCountVo projectCountVo = homeService.allCount();
            if (projectCountVo == null)
                return Result.ok();
            return Result.ok(projectCountVo);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/allCount1")
    public List<ProjectCountVo> allCount1() {
        return homeService.allProjectCountVo();
    }

    @GetMapping("/allCount2")
    public List<ProjectCountVo> allCount2() {
        return homeService.allProjectCountVo1();
    }


//    /*
//    * 柱状图
//    * */
//
//    @GetMapping("/barChart/listBar")
//    @ResponseBody
//    public List  listBar() {
//        //用对象接收返回值，对象存在list里
//        List<ProjectCountVo> list  = homeService.barList();
//        List datalist = new ArrayList<>();
//        //数据库类型和出现次数
//        String names= null;
//        int interfaceCount = 0 , caseCount = 0;
//        //遍历list拿到每个对象中需要的，dbtype和count
//        for(int i = 0 ; i < list.size() ; i++) {
//            names=list.get(i).getName1();
//            interfaceCount = list.get(i).getInterfaceCount1();
//            caseCount = list.get(i).getInterfaceCount1();
//            datalist.add(names);
//            datalist.add(interfaceCount);
//            datalist.add(caseCount);
//        }
//        return datalist;
//    }
}
