package com.example.demo.mock.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.mock.entity.vo.DataVo;
import com.example.demo.mock.entity.vo.SearchVo;
import org.springframework.stereotype.Repository;

@Repository
public interface DataService {
    /*
    * 访问数据统计
    * */
    void dataStatistic();

    /*
    * 分页条件查询多条接口
    * @params SearchVo 实例对象
    * */
    IPage<DataVo> selectApi(SearchVo searchVo ,Integer pageIndex,Integer pageSize);

    /*
     * 分页条件查询-请求方数据访问
     * @params SearchVo 实例对象
     * */
    IPage<DataVo> selectSys(SearchVo searchVo ,Integer pageIndex,Integer pageSize);
}
