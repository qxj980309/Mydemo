package com.example.demo.mock.common.Handler;

import com.alibaba.fastjson.TypeReference;
import com.example.demo.mock.entity.po.ExcelPO;
import org.apache.ibatis.type.JdbcType;
import org.apache.poi.ss.formula.functions.T;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
* 类型处理器
* */
public class ExcelPOTypeHandler extends ListTypeHandler<ExcelPO> {

    @Override
    protected TypeReference<ExcelPO> getTypeReference() {
        return new TypeReference<ExcelPO>(){

        };
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ExcelPO parameter, JdbcType jdbcType) throws SQLException {

    }
}

