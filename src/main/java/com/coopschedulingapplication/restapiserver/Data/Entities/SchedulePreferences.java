package com.coopschedulingapplication.restapiserver.Data.Entities;

import java.util.Map;

public class SchedulePreferences {
    private final Integer userId;
    private final Integer prefWeekDays;
    private final Integer maxWeekDays;


    public SchedulePreferences(Integer userId, Integer prefWeekDays, Integer maxWeekDays) {
        this.userId = userId;
        this.prefWeekDays = prefWeekDays;
        this.maxWeekDays = maxWeekDays;
    }

    public SchedulePreferences(Map<String,Object> json){
        this.userId = (Integer) json.get("userId");
        this.prefWeekDays = (Integer) json.get("prefWeekDays");
        this.maxWeekDays = (Integer) json.get("maxWeekDays");
    }

    public Map<String, Object> toJson(){
        return Map.of(
                "userId",userId,
                "prefWeekDays",prefWeekDays,
                "maxWeekDays",maxWeekDays
        );
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getPrefWeekDays() {
        return prefWeekDays;
    }

    public Integer getMaxWeekDays() {
        return maxWeekDays;
    }
}
