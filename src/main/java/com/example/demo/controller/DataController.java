package com.example.demo.controller;

import cn.hutool.log.LogFactory;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.common.result.Result;
import com.example.demo.entity.vo.DataVo;
import com.example.demo.entity.vo.SearchVo;
import com.example.demo.service.DataService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping
@Api(tags = "访问数据统计")
public class DataController {
    @Resource
    private DataService dataService;

    private static final Logger log = LoggerFactory.getLogger(DataController.class);

    /*分页查询多条接口访问数据*/
    Result<IPage<DataVo>> selectApi(SearchVo searchVo, @RequestParam Integer pageIndex, @RequestParam Integer pageSize){
        return Result.ok(dataService.selectApi(searchVo, pageIndex, pageSize));
    }
    /*分页查询多条请求-接收方访问数据*/
    Result<IPage<DataVo>> selectSys(SearchVo searchVo, @RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
        return Result.ok(dataService.selectSys(searchVo, pageIndex, pageSize));
    }
}
