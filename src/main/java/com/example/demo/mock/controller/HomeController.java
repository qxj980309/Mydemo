package com.example.demo.mock.controller;

import com.example.demo.common.result.Result;
import com.example.demo.mock.entity.vo.ProjectCountVo;
import com.example.demo.mock.service.HomeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Resource
    private HomeService homeService;

    @GetMapping("/allCount")
    Result<ProjectCountVo> allCount() {
        try {
            ProjectCountVo projectCountVo = homeService.allCount();
            if (projectCountVo == null)
                return Result.ok();
            return Result.ok(projectCountVo);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
