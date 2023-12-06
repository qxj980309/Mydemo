package com.example.demo.mock.common.Handler;

import com.alibaba.fastjson.TypeReference;
import com.example.demo.mock.entity.po.PretreatmentPO;
import org.apache.ibatis.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PretreatmentPOTypeHandler extends ListTypeHandler<PretreatmentPO>{
    @Override
    protected TypeReference<PretreatmentPO> getTypeReference() {
        return new TypeReference<PretreatmentPO>(){

        };
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, PretreatmentPO parameter, JdbcType jdbcType) throws SQLException {

    }
}
