package com.example.demo.mock.service;

import com.example.demo.mock.entity.po.CommonPO;
import org.springframework.stereotype.Repository;

import java.util.function.Function;

@Repository
public interface CommonService {
    /*
    * 判斯当前用户是否和创建用户一致
    *
    *  @param id 数据记录id
    *  @param getUserMethod 获取数据记的方法
    *  @return true if not match, else false
    * */
    boolean notMatch(Long id, Function<Long, CommonPO> getUserMethod);
}
