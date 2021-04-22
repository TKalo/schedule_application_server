package com.coopschedulingapplication.restapiserver.DataObjects;

import java.util.Map;

public class SchedulePreferences {
    Integer userId;
    Integer prefWeekDays;
    Integer maxWeekDays;


    public SchedulePreferences(Integer userId, Integer prefWeekDays, Integer maxWeekDays) {
        this.userId = userId;
        this.prefWeekDays = prefWeekDays;
        this.maxWeekDays = maxWeekDays;
    }

    public static SchedulePreferences fromJson(Map<String,Object> json){
        return new SchedulePreferences(
                (Integer) json.get("userId"),
                (Integer) json.get("prefWeekDays"),
                (Integer) json.get("maxWeekDays")
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
