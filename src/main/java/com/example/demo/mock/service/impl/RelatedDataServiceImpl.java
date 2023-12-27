package com.example.demo.mock.service.impl;

import com.example.demo.mock.entity.po.RelatedApiPO;
import com.example.demo.mock.service.RelatedDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RelatedDataServiceImpl implements RelatedDataService {
    @Override
    public List<RelatedApiPO> selectRelatedDate(Long caseId) {
        return null;
    }

    @Override
    public List<RelatedApiPO> selectRelatedData(List<String> keyList) {
        return null;
    }
}
