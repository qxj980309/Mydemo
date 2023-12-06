package com.example.demo.mock.common.Handler;

import com.alibaba.fastjson.TypeReference;
import com.example.demo.mock.entity.po.OperationTypePO;
import org.apache.ibatis.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OperationTypePOTypeHandler extends ListTypeHandler<OperationTypePO>{
    @Override
    protected TypeReference<OperationTypePO> getTypeReference() {
        return new TypeReference<OperationTypePO>(){

        };
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, OperationTypePO parameter, JdbcType jdbcType) throws SQLException {

    }
}
