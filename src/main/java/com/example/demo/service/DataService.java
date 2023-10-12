package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.vo.DataVo;
import com.example.demo.entity.vo.SearchVo;

public interface DataService {
    IPage<DataVo> selectApi(SearchVo searchVo ,Integer pageIndex,Integer pageSize);

    IPage<DataVo> selectSys(SearchVo searchVo ,Integer pageIndex,Integer pageSize);
}
