package com.example.demo.mock.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.common.result.Result;
import com.example.demo.mock.common.entity.MockResponse;
import com.example.demo.mock.entity.po.CommonMessagePO;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Repository
public interface CommonMessageService {
    /*
    * 通过id查询单挑数据
    * */
    CommonMessagePO selectById(Long id);

    /*
    * 条件查询多杂歌鹅
    *
    *  @param commonMessagePo 实对象
    * @return 对象列表
    * */
    List<CommonMessagePO> selectList (CommonMessagePO commonMessagePO);

    /*
    * 分页条件查询多条数据
    *
    *  @param CommonMessagePO 实例对象
    *  @return 对象列表
    * */
    IPage<CommonMessagePO> selectPage(CommonMessagePO commonMessagePO, Integer pageIndex, Integer pageSize);

    /*
    *  新增数据
    *
    *  @param CommonMessagePO 实例对象
    * @return 对象列表
    * */
    Result<?> insert(CommonMessagePO commonMessagePO);

    /*
    * 修改数据
    *
    *  @param file 修改文件
    *  @param CommonMessagePO 实例对象
    *  @return 更新结果
    * */
    Result<?> updateById(MultipartFile file, CommonMessagePO commonMessagePO);

    /*
    * 报文新增成删除
    *
    * @param CommonMessagePO 实例对象
    * @return 更新结果
    * */
    Result<?> updateOrDeleteById(CommonMessagePO commonMessagePO);

    /*
    *  通过主健删除数据
    *
    *  @param id 主键
    * @return 删除的条数
    * */
    Result<?> deleteById(Long id);

    /*
    * 导入公共报文的信息
    *
    *  @param CommonMessagePO 接口信息
    *  @param file 文件
    *  @return 更新结果
    * */
    Result<?> uploadOne(CommonMessagePO commonMessagePO, MultipartFile file);
}
