package com.example.demo.mock.common.Handler;

import com.alibaba.fastjson.TypeReference;
import com.example.demo.mock.entity.po.InterceptPO;
import org.apache.ibatis.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InterceptPOTypeHandler extends ListTypeHandler<InterceptPO> {
    @Override
    protected TypeReference<InterceptPO> getTypeReference() {
        return new TypeReference<InterceptPO>(){

        };
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, InterceptPO parameter, JdbcType jdbcType) throws SQLException {

    }
}
