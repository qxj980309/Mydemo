package com.example.demo.controller;


import com.example.demo.common.result.Result;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Api(tags = "用户接口类")
@RestController
@RequestMapping("/user")
public class UserController {
    //MyBatis
    @Resource
    private UserService userService;

    @PostMapping("/add")
    public Result add(User user){
//        public Result add(@Validated User user){  @Validated  用来校验字段是不是为空搭配User中得到NotBlank、NotNull使用
        return userService.add(user);
    }

    @GetMapping("/info")
    public Result getUserInfo(@RequestParam int userId){
        return userService.findUser(userId);
    }

    /**
     * 用户信息列表
     */
//    @GetMapping("/list")
//    public List<User> list() {
//        return userService.list();
//    }
    @GetMapping("/list")
    public Result<User> list() {
        return userService.finaAll();
    }

}
