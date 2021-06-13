package com.coopschedulingapplication.restapiserver.persistence.Postgres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PostgresGenericFunctions {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public Map<String, Object> queryMap(String sql, Map<String, Object> params){
        MapSqlParameterSource sqlParams = HelperFunctions.map2SqlMap(params);

        Map<String, Object> sqlMap = jdbcTemplate.queryForMap(sql, sqlParams);

        return HelperFunctions.snake2Camel(sqlMap);
    }

    public List<Map<String, Object>> queryList(String sql, Map<String, Object> params){
        MapSqlParameterSource sqlParams = HelperFunctions.map2SqlMap(params);

        List<Map<String, Object>> sqlMapList = jdbcTemplate.queryForList(sql, sqlParams);

        return HelperFunctions.snake2Camel(sqlMapList);
    }


}
