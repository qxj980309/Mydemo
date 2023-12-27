package com.example.demo.mock.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.mock.entity.po.CommonMessagePO;
import com.example.demo.mock.service.CommonMessageService;
import com.example.demo.myself.common.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CommonMessageServiceImpl implements CommonMessageService {
    @Override
    public CommonMessagePO selectById(Long id) {
        return null;
    }

    @Override
    public List<CommonMessagePO> selectList(CommonMessagePO commonMessagePO) {
        return null;
    }

    @Override
    public IPage<CommonMessagePO> selectPage(CommonMessagePO commonMessagePO, Integer pageIndex, Integer pageSize) {
        return null;
    }

    @Override
    public Result<?> insert(CommonMessagePO commonMessagePO) {
        return null;
    }

    @Override
    public Result<?> updateById(MultipartFile file, CommonMessagePO commonMessagePO) {
        return null;
    }

    @Override
    public Result<?> updateOrDeleteById(CommonMessagePO commonMessagePO) {
        return null;
    }

    @Override
    public Result<?> deleteById(Long id) {
        return null;
    }

    @Override
    public Result<?> uploadOne(CommonMessagePO commonMessagePO, MultipartFile file) {
        return null;
    }
}
