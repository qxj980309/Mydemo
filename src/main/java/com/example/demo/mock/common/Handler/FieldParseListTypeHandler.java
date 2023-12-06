package com.example.demo.mock.common.Handler;

import com.alibaba.fastjson.TypeReference;
import com.example.demo.mock.entity.po.FieldParsePO;
import org.apache.ibatis.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FieldParseListTypeHandler extends ListTypeHandler<FieldParsePO>{
    @Override
    protected TypeReference<FieldParsePO> getTypeReference() {
        return new TypeReference<FieldParsePO>(){

        };
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, FieldParsePO parameter, JdbcType jdbcType) throws SQLException {

    }
}
