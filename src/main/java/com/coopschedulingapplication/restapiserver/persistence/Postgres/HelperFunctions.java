package com.coopschedulingapplication.restapiserver.persistence.Postgres;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelperFunctions {

    public static Map<String, Object> snake2Camel(Map<String, Object> snake){
        Map<String, Object> camel = new HashMap<>();
        snake.forEach((snakeKey, object) -> {
            while (snakeKey.contains("_")){
                snakeKey = snakeKey.replaceFirst("_[a-z]",String.valueOf(Character.toUpperCase(snakeKey.charAt(snakeKey.indexOf("_") + 1))));
            }
            String camelKey = snakeKey;
            camel.put(camelKey,object);
        });
        return camel;
    }

    public static MapSqlParameterSource map2SqlMap(Map<String, Object> map){
        MapSqlParameterSource sqlMap = new MapSqlParameterSource();
        map.forEach((key, object) -> {
            if(!List.of(Integer.class, Double.class, String.class, Long.class).contains(object.getClass())){
                sqlMap.addValue(key, object, Types.OTHER);
            }else {
                sqlMap.addValue(key, object);
            }
        });
        return sqlMap;
    }

    static <T> T successOrNull(ITry<T> run){
        try {
            return run.iTry();
        }catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    interface ITry<T>{
        T iTry();
    }
}
