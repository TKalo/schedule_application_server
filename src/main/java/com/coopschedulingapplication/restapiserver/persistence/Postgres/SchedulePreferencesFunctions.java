package com.coopschedulingapplication.restapiserver.persistence.Postgres;

import com.coopschedulingapplication.restapiserver.Data.Entities.SchedulePreferences;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SchedulePreferencesFunctions {

    NamedParameterJdbcTemplate jdbcTemplate;

    public SchedulePreferences set(SchedulePreferences preferences) {
        return HelperFunctions.successOrNull(()-> {
            Map<String, Object> sqlMap = jdbcTemplate.queryForMap("UPDATE schedule_preferences SET pref_week_days = :prefWeekDays, max_week_days=:maxWeekDays WHERE user_id = :userId RETURNING *", HelperFunctions.map2SqlMap(preferences.toJson()));
            Map<String, Object> parsableMap = HelperFunctions.snake2Camel(sqlMap);
            return SchedulePreferences.fromJson(parsableMap);
        });
    }

    public List<SchedulePreferences> getByStore(int storeId) {
        return HelperFunctions.successOrNull(()-> {
            List<Map<String, Object>> sqlMapList = jdbcTemplate.queryForList("SELECT * FROM schedule_preferences WHERE user_id IN (SELECT user_id FROM user_table WHERE store_id = :storeId)",Map.of("store_id",storeId));
            return sqlMapList.stream().map(sqlMap -> {
                Map<String, Object> parsableMap = HelperFunctions.snake2Camel(sqlMap);
                return SchedulePreferences.fromJson(parsableMap);
            }).collect(Collectors.toList());
        });
    }

    public SchedulePreferences getByUser(int userId) {
        return HelperFunctions.successOrNull(()-> {
            Map<String, Object> sqlMap = jdbcTemplate.queryForMap("SELECT * FROM schedule_preferences WHERE user_id=:userId",Map.of("user_id",userId));
            Map<String, Object> parsableMap = HelperFunctions.snake2Camel(sqlMap);
            return SchedulePreferences.fromJson(parsableMap);
        });
    }
}
