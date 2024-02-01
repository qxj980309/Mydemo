package com.example.demo.myself.controller;

import com.example.demo.myself.common.result.Result;
import com.example.demo.myself.entity.User;
import com.example.demo.myself.service.UserService;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import com.example.demo.myself.common.util.JWTUtil;

@Api(tags = "用户接口类")
@RestController
@RequestMapping("/user")
public class UserController {
    //MyBatis
    @Resource
    private UserService userService;

    @Resource
    private JWTUtil jwtUtil;

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

    /*
    * 测试一下jwt
    * */
    @GetMapping("/jwt/test")
    public User jwtTest(HttpServletRequest request){
        String token = request.getHeader("Token");
        User user = jwtUtil.verify(token);
        return user;
    }

    /*
    * login方法生成一下Token
    * */
    @GetMapping("/jwt/login")
    public String jwtLogin(String username,String password){
        User user = userService.getByUsername(username,password);
//        if(Objects.nonNull(user)) {
//            if (!user.getPassword().equals(password)) {
//                return "密码错误！";
//            }
//        }
        String token = jwtUtil.createToken(user);
        return token;
    }

}
