package com.example.demo.mock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.mock.entity.po.DataPo;
import com.example.demo.mock.entity.vo.DataVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface DataServiceMapper extends BaseMapper<DataPo> {
//    IPage<DataVo> selectData(Page page,@Param("sendSysCode") String sendSysCode,@Param("accSysCode") String accSysCode,@Param("date")String date);
}
