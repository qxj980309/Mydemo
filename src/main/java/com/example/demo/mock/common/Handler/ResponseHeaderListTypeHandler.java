package com.example.demo.mock.common.Handler;

import com.alibaba.fastjson.TypeReference;
import com.example.demo.mock.entity.po.ResponseHeaderPO;
import org.apache.ibatis.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResponseHeaderListTypeHandler extends ListTypeHandler<ResponseHeaderPO>{
    @Override
    protected TypeReference<ResponseHeaderPO> getTypeReference() {
        return new TypeReference<ResponseHeaderPO>(){

        };
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ResponseHeaderPO parameter, JdbcType jdbcType) throws SQLException {

    }
}
