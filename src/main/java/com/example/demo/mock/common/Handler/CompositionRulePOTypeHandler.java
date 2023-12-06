package com.example.demo.mock.common.Handler;

import com.alibaba.fastjson.TypeReference;
import com.example.demo.mock.entity.po.CompositionRulePO;
import org.apache.ibatis.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CompositionRulePOTypeHandler extends ListTypeHandler<CompositionRulePO> {
    @Override
    protected TypeReference<CompositionRulePO> getTypeReference() {
        return new TypeReference<CompositionRulePO>(){

        };
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, CompositionRulePO parameter, JdbcType jdbcType) throws SQLException {

    }
}
