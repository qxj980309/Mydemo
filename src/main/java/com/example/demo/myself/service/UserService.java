package com.example.demo.myself.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.myself.common.result.Result;
import com.example.demo.myself.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserService extends IService<User>{
  Result add(User user);

  Result findUser(int userId);

//  List<User> list();
  Result<User> finaAll();

  User getByUsername(String username,String password);
}
