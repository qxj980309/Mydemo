package com.example.demo.mock.common.Handler;

import com.alibaba.fastjson.TypeReference;
import com.example.demo.mock.entity.po.ExpectationPO;
import org.apache.ibatis.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ExpectListTypeHandler extends ListTypeHandler<ExpectationPO>{
    @Override
    protected TypeReference<ExpectationPO> getTypeReference() {
        return  new TypeReference<ExpectationPO>(){

        };
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ExpectationPO parameter, JdbcType jdbcType) throws SQLException {

    }
}
