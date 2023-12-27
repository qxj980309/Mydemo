package com.example.demo.mock.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.myself.common.result.Result;
import com.example.demo.mock.entity.po.CommonRulePO;
import com.example.demo.mock.entity.vo.CommonRuleUseVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
* 规则维护表(tb_common_rule)服务接口
* */
@Repository
public interface CommonRuleService {
    /*
    * 通过TD查询单杂数船
    *
    * @param id 主键
    * @return 实例对象
    * */
    CommonRulePO selectById(Long id);

    /*
    * 条件查询多条数据
    *
    *@param commonRuLePO 实例对象
    * @return 对象列表
    * */
    List<CommonRulePO> selectList(CommonRulePO commonRulePO);

    /*
    * 分页条件查询多条数据
    *
    *  @param commonRuleP0 实例对象
    *  @return 对象列表
    * */
    IPage<CommonRulePO> selectPage(CommonRulePO commonRulePO, Integer pageIndex, Integer pageSize);

    /*
    * 新增数据
    *
    *  @param commonRulePo 实好家
    * @return 好家利悲
    * */
    Result<?> insert(CommonRulePO commonRulePO);

    /*
    * 修改数书
    *
    *  @param commonRuleP0 例对家
    * @return 更新的条数
    * */
    Result<?> updateById(CommonRulePO commonRulePO);

    /*
    * 删除数据
    *
    * @param id 主键
    * @return 删除的条数
    * */
    Result<?> deleteById(Long id);

    /*
    * 查询公共规则枚举值
    *
    * @return 对象列表
    * */
    List<CommonRulePO> selectRuleEnum();

    /*
    * 分页查询多案例使用规则
    *
    * @param CommonRulePO
    * @return List
    * */
    IPage<CommonRuleUseVO> selectPageRuleUse(CommonRuleUseVO commonRuleUseVO, Integer pageIndex, Integer pageSize);

    /*
    * 同步更新规则至使用案例
    *
    * @param CommonRuleUseList
    * */
    Result<?> updateRuleByCaseId(List<CommonRuleUseVO> commonRuleUseVOList);
}
