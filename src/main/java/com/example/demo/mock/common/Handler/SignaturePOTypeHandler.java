package com.example.demo.mock.common.Handler;

import com.alibaba.fastjson.TypeReference;
import com.example.demo.mock.entity.po.SignaturePO;
import org.apache.ibatis.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignaturePOTypeHandler extends ListTypeHandler<SignaturePO>{
    @Override
    protected TypeReference<SignaturePO> getTypeReference() {
        return new TypeReference<SignaturePO>(){

        };
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, SignaturePO parameter, JdbcType jdbcType) throws SQLException {

    }
}
