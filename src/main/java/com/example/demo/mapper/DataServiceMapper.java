package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.DataPo;
import com.example.demo.entity.vo.DataVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DataServiceMapper extends BaseMapper<DataPo> {
    //接口访问情况汇总聚合分页sql
//    IPage<DataVo> selectPageApi(Page page,@Param("sendSysCode") String sendSysCode,@Param("accSysCode") String accSysCode,
//                                @Param("name")String name, @Param("txCode") String txCode, @Param("date")String date);
//
//    //系统访问情况汇总聚合分页sql
//    IPage<DataVo> selectPageSys(Page page,@Param("sendSysCode") String sendSysCode,@Param("accSysCode") String accSysCode,
//                                @Param("date")String date);
}
