package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.DataPo;
import com.example.demo.entity.vo.DataVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface DataServiceMapper extends BaseMapper<DataPo> {
    IPage<DataVo> selectData(Page page,@Param("sendSysCode") String sendSysCode,@Param("accSysCode") String accSysCode,@Param("date")String date);
}
