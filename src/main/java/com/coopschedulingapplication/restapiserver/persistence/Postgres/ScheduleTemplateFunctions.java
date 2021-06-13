package com.coopschedulingapplication.restapiserver.persistence.Postgres;

import com.coopschedulingapplication.restapiserver.Data.Entities.ScheduleTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ScheduleTemplateFunctions {

    @Autowired
    PostgresGenericFunctions PGF;

    public ScheduleTemplate set(ScheduleTemplate template) {
        String sql = "UPDATE schedule_template SET preference_deadline = :preferenceDeadline, creation_deadline=:creationDeadline, initiation_deadline=:initiationDeadline, weeks=:weeks WHERE store_id = :storeId RETURNING *";
        Map<String, Object> params = template.toJson();
        return HelperFunctions.successOrNull(()-> new ScheduleTemplate(PGF.queryMap(sql,params)));
    }

    public ScheduleTemplate getByStore(int storeId) {
        String sql = "SELECT * FROM schedule_template WHERE store_id=:storeId";
        Map<String, Object> params = Map.of("storeId", storeId);
        return HelperFunctions.successOrNull(()-> new ScheduleTemplate(PGF.queryMap(sql,params)));
    }
}
