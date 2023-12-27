package com.example.demo.mock.service;

import com.example.demo.mock.entity.po.RelatedApiPO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelatedDataService {
    /*
    * 根据caseId查询
    * @param caseId
    * @return List<RelatedApiPO>
    * */
    List<RelatedApiPO> selectRelatedDate(Long caseId);

    /*
     * 根据key查询
     * @param keyList
     * @return List<RelatedApiPO>
     * */
    List<RelatedApiPO> selectRelatedData(List<String> keyList);
}
