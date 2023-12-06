package com.example.demo.mock.common.Handler;

import com.alibaba.fastjson.TypeReference;
import com.example.demo.mock.entity.po.DynamicMsgPO;
import org.apache.ibatis.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DynamicMsgListTypeHandler extends ListTypeHandler<DynamicMsgPO>{
    @Override
    protected TypeReference<DynamicMsgPO> getTypeReference() {
        return new TypeReference<DynamicMsgPO>(){

        };
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, DynamicMsgPO parameter, JdbcType jdbcType) throws SQLException {

    }
}
