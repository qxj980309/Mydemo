package com.example.demo.mock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.mock.entity.po.RelatedApiPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RelatedApiMapper extends BaseMapper<RelatedApiPO> {
}
