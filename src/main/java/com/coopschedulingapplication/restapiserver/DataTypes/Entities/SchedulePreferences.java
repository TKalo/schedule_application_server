package com.coopschedulingapplication.restapiserver.DataTypes.Entities;

import com.coopschedulingapplication.restapiserver.DataTypes.HelperFunctions;

import java.util.HashMap;
import java.util.Map;

public class SchedulePreferences {
    private final Integer userId;
    private final Integer prefWeekDays;
    private final Integer maxWeekDays;

    public SchedulePreferences(Map<String,Object> json){
        this.userId = (Integer) json.get("userId");
        this.prefWeekDays = (Integer) json.get("prefWeekDays");
        this.maxWeekDays = (Integer) json.get("maxWeekDays");
    }

    public Map<String, Object> toJson(){
        HashMap<String,Object> jsonMap = new HashMap<>();
        HelperFunctions.addIfNotNull(jsonMap,"userId",userId);
        HelperFunctions.addIfNotNull(jsonMap,"prefWeekDays",prefWeekDays);
        HelperFunctions.addIfNotNull(jsonMap,"maxWeekDays",maxWeekDays);
        return jsonMap;
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
