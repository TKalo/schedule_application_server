package com.coopschedulingapplication.restapiserver.persistence.Postgres;

import com.coopschedulingapplication.restapiserver.Data.Entities.ScheduleTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ScheduleTemplateFunctions {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public ScheduleTemplate set(ScheduleTemplate template) {

        return HelperFunctions.successOrNull(()-> {
            Map<String, Object> sqlMap = jdbcTemplate.queryForMap("UPDATE schedule_template SET preference_deadline = :preferenceDeadline, creation_deadline=:creationDeadline, initiation_deadline=:initiationDeadline, weeks=:weeks WHERE store_id = :storeId RETURNING *", HelperFunctions.map2SqlMap(template.toJson()));
            Map<String, Object> parsableMap = HelperFunctions.snake2Camel(sqlMap);
            return new ScheduleTemplate(parsableMap);
        });
    }

    public ScheduleTemplate getByStore(int storeId) {
        return HelperFunctions.successOrNull(()-> {
            Map<String, Object> sqlMap = jdbcTemplate.queryForMap("SELECT * FROM schedule_template WHERE store_id=:storeId",Map.of("store_id",storeId));
            Map<String, Object> parsableMap = HelperFunctions.snake2Camel(sqlMap);
            return new ScheduleTemplate(parsableMap);
        });
    }

}
