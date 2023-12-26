package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.common.result.Result;
import com.example.demo.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserService extends IService<User>{
  Result add(User user);

  Result findUser(int userId);

//  List<User> list();
  Result<User> finaAll();

  User getByUsername(String username);
}
