package com.example.demo.mock.common.Handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.poi.ss.formula.functions.T;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.ResultSet;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@MappedTypes({List.class})
public  abstract class ListTypeHandler<T> extends BaseTypeHandler<T> {
    private static final PGobject jsonObject = new PGobject();

    public void setNullParameter(PreparedStatement preparedStatement, int i, T o, JdbcType jdbcType) throws SQLException{
        if (null != preparedStatement) {
            jsonObject.setType("json");
            jsonObject.setValue(JSON.toJSONString(o));
            preparedStatement.setObject(i,jsonObject);
        }
    }

    public T getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return this.toObject(resultSet.getString(s));
    }

    public T getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return this.toObject(resultSet.getString(i));
    }

    public T getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return this.toObject(callableStatement.getString(i));
    }

    /*
    * json转换为实体
    *
    * @param resultSet json String
    * @return 实体类
     * */
    private T toObject(String resultSet){
        if(!StringUtils.isNotBlank(resultSet)){
            return null;
        }
        return JSON.parseObject(resultSet,getTypeReference());
    }

    protected abstract TypeReference<T> getTypeReference();


}
