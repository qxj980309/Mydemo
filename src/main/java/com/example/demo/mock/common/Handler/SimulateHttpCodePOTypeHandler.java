package com.example.demo.mock.common.Handler;

import com.alibaba.fastjson.TypeReference;
import com.example.demo.mock.entity.po.ExcelPO;
import com.example.demo.mock.entity.po.SimulateHttpCodePO;
import org.apache.ibatis.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SimulateHttpCodePOTypeHandler extends ListTypeHandler<SimulateHttpCodePO>{
    @Override
    protected TypeReference<SimulateHttpCodePO> getTypeReference() {
        return new TypeReference<SimulateHttpCodePO>(){

        };
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, SimulateHttpCodePO parameter, JdbcType jdbcType) throws SQLException {

    }
}
