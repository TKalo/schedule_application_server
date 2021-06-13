package com.coopschedulingapplication.restapiserver.persistence.Postgres;

import com.coopschedulingapplication.restapiserver.Data.Entities.SchedulePreferences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SchedulePreferencesFunctions {

    @Autowired
    PostgresGenericFunctions PGF;

    public SchedulePreferences set(SchedulePreferences preferences) {
        String sql = "UPDATE schedule_preferences SET pref_week_days = :prefWeekDays, max_week_days=:maxWeekDays WHERE user_id = :userId RETURNING *";
        Map<String, Object> params = preferences.toJson();
        return HelperFunctions.successOrNull(()-> new SchedulePreferences(PGF.queryMap(sql,params)));
    }

    public List<SchedulePreferences> getByStore(int storeId) {
        String sql = "SELECT * FROM schedule_preferences WHERE user_id IN (SELECT user_id FROM user_table WHERE store_id = :storeId)";
        Map<String, Object> params = Map.of("store_id",storeId);
        return HelperFunctions.successOrNull(()-> PGF.queryList(sql,params).stream().map(SchedulePreferences::new).collect(Collectors.toList()));
    }

    public SchedulePreferences getByUser(int userId) {
        String sql = "SELECT * FROM schedule_preferences WHERE user_id=:userId";
        Map<String, Object> params = Map.of("user_id",userId);
        return HelperFunctions.successOrNull(()-> new SchedulePreferences(PGF.queryMap(sql,params)));
    }
}
