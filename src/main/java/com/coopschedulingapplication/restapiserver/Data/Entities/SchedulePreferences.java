package com.coopschedulingapplication.restapiserver.Data.Entities;

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
        if(userId != null) jsonMap.put("userId",userId);
        if(prefWeekDays != null) jsonMap.put("prefWeekDays",prefWeekDays);
        if(maxWeekDays != null) jsonMap.put("maxWeekDays",maxWeekDays);
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
