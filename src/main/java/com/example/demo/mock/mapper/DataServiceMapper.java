package com.example.demo.mock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.mock.entity.po.DataPO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface DataServiceMapper extends BaseMapper<DataPO> {
//    IPage<DataVo> selectData(Page page,@Param("sendSysCode") String sendSysCode,@Param("accSysCode") String accSysCode,@Param("date")String date);
}
