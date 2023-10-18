package com.example.demo.mock.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.mock.entity.vo.DataVo;
import com.example.demo.mock.entity.vo.SearchVo;
import org.springframework.stereotype.Repository;

@Repository
public interface DataService {
    IPage<DataVo> selectApi(SearchVo searchVo ,Integer pageIndex,Integer pageSize);

    IPage<DataVo> selectSys(SearchVo searchVo ,Integer pageIndex,Integer pageSize);
}
