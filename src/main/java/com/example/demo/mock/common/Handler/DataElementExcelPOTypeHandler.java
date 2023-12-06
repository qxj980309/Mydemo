package com.example.demo.mock.common.Handler;

import com.alibaba.fastjson.TypeReference;
import com.example.demo.mock.entity.po.DataElementExcelPO;
import org.apache.ibatis.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DataElementExcelPOTypeHandler extends ListTypeHandler<List<DataElementExcelPO>>{
    @Override
    protected TypeReference<List<DataElementExcelPO>> getTypeReference() {
        return new TypeReference<List<DataElementExcelPO>> (){

        };
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<DataElementExcelPO> parameter, JdbcType jdbcType) throws SQLException {

    }
}
