package com.example.demo.mock.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.mock.entity.po.DynamicMsgPO;
import com.example.demo.mock.entity.po.InterfaceCasePO;
import com.example.demo.mock.entity.vo.InterfaceCaseVO;
import com.example.demo.mock.service.InterfaceCaseService;
import com.example.demo.myself.common.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InterfaceCaseServiceImpl implements InterfaceCaseService {
    @Override
    public InterfaceCasePO selectById(Long id) {
        return null;
    }

    @Override
    public List<InterfaceCasePO> selectList(InterfaceCasePO interfaceCasePO) {
        return null;
    }

    @Override
    public List<InterfaceCasePO> selectByInterfaceId(Long interfaceId) {
        return null;
    }

    @Override
    public IPage<InterfaceCaseVO> selectPage(InterfaceCaseVO interfaceCaseVO, Integer pageIndex, Integer pageSize) {
        return null;
    }

    @Override
    public Result<?> insert(InterfaceCaseVO interfaceCaseVO) {
        return null;
    }

    @Override
    public Result<?> updateById(InterfaceCaseVO interfaceCaseVO) {
        return null;
    }

    @Override
    public Result<?> deleteById(Long id) {
        return null;
    }

    @Override
    public void ruleIdUpdateNull(Long ruleId, Long id) {

    }

    @Override
    public Integer updateCasePoById(InterfaceCasePO interfaceCasePO) {
        return null;
    }

    @Override
    public void dynamicMsgUpdate(Long id, List<DynamicMsgPO> dynamicMsgPOList) {

    }
}
