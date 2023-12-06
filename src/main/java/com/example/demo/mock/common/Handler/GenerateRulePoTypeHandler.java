package com.example.demo.mock.common.Handler;

import com.alibaba.fastjson.TypeReference;
import com.example.demo.mock.entity.po.GenerateRulePO;
import org.apache.ibatis.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GenerateRulePoTypeHandler extends ListTypeHandler<GenerateRulePO> {
    @Override
    protected TypeReference<GenerateRulePO> getTypeReference() {
        return new TypeReference<GenerateRulePO>(){

        };
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, GenerateRulePO parameter, JdbcType jdbcType) throws SQLException {

    }
}
