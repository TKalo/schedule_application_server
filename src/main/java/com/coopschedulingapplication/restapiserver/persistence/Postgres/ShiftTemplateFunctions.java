package com.coopschedulingapplication.restapiserver.persistence.Postgres;

import com.coopschedulingapplication.restapiserver.Data.Entities.ShiftTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ShiftTemplateFunctions {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public ShiftTemplate add(ShiftTemplate template, Principal principal) {
        return HelperFunctions.successOrNull(()-> {
            MapSqlParameterSource sqlInputMap = HelperFunctions.map2SqlMap(template.toJson());
            sqlInputMap.addValue("userId", Integer.parseInt(principal.getName()));
            Map<String, Object> sqlOutputMap = jdbcTemplate.queryForMap("INSERT INTO shift_template (week_day, start_time, end_time, store_id, worker_type) VALUES(:weekDay, :startTime, :endTime, (SELECT store_id FROM user_table WHERE id = :userId), :workerType) RETURNING *", sqlInputMap);
            Map<String, Object> parsableMap = HelperFunctions.snake2Camel(sqlOutputMap);
            return ShiftTemplate.fromJson(parsableMap);
        });
    }

    public ShiftTemplate update(ShiftTemplate template) {
        return HelperFunctions.successOrNull(()-> {
            Map<String, Object> sqlMap = jdbcTemplate.queryForMap("UPDATE shift_template SET week_day = :weekDay, start_time = :startTime, end_time = :endTime, worker_type = :workerType WHERE id = :id RETURNING *", HelperFunctions.map2SqlMap(template.toJson()));
            Map<String, Object> parsableMap = HelperFunctions.snake2Camel(sqlMap);
            return ShiftTemplate.fromJson(parsableMap);
        });
    }

    public ShiftTemplate delete(ShiftTemplate template) {
        return HelperFunctions.successOrNull(()-> {
            Map<String, Object> sqlMap = jdbcTemplate.queryForMap("WITH t1 AS (DELETE FROM shift_template WHERE id = :id RETURNING *) SELECT * FROM t1", HelperFunctions.map2SqlMap(template.toJson()));
            Map<String, Object> parsableMap = HelperFunctions.snake2Camel(sqlMap);
            return ShiftTemplate.fromJson(parsableMap);
        });
    }

    public List<ShiftTemplate> getByStore(int storeId) {
        return HelperFunctions.successOrNull(()-> {
            List<Map<String, Object>> sqlMapList = jdbcTemplate.queryForList("SELECT * FROM shift_template WHERE store_id=:storeId", Map.of("storeId", storeId));
            return sqlMapList.stream().map(sqlMap -> {
                Map<String, Object> parsableMap = HelperFunctions.snake2Camel(sqlMap);
                return ShiftTemplate.fromJson(parsableMap);
            }).collect(Collectors.toList());
        });
    }

}
