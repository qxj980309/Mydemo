package com.example.demo.mock.common.Handler;

import com.alibaba.fastjson.TypeReference;
import com.example.demo.mock.entity.po.IsRelatedPO;
import org.apache.ibatis.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IsRelatedPOTypeHandler extends ListTypeHandler<IsRelatedPO> {
    @Override
    protected TypeReference<IsRelatedPO> getTypeReference() {
        return new TypeReference<IsRelatedPO>(){

        };
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, IsRelatedPO parameter, JdbcType jdbcType) throws SQLException {

    }
}
