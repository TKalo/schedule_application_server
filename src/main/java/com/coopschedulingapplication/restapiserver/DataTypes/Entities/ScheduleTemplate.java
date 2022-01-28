package com.coopschedulingapplication.restapiserver.Data.Entities;

import com.coopschedulingapplication.restapiserver.Data.HelperFunctions;

import java.util.HashMap;
import java.util.Map;

public class ScheduleTemplate {
    private final Integer storeId;
    private final Integer weeks;
    private final Long preferenceDeadline;
    private final Long creationDeadline;
    private final Long initiationDeadline;


    public ScheduleTemplate(Map<String, Object> json){
        this.storeId = (Integer) json.get("storeId");
        this.weeks = (Integer) json.get("weeks");
        this.preferenceDeadline = (Long) json.get("preferenceDeadline");
        this.creationDeadline = (Long) json.get("creationDeadline");
        this.initiationDeadline = (Long) json.get("initiationDeadline");
    }

    public Map<String, Object> toJson(){
        HashMap<String,Object> jsonMap = new HashMap<>();
        HelperFunctions.addIfNotNull(jsonMap,"storeId",storeId);
        HelperFunctions.addIfNotNull(jsonMap,"weeks",weeks);
        HelperFunctions.addIfNotNull(jsonMap,"preferenceDeadline",preferenceDeadline);
        HelperFunctions.addIfNotNull(jsonMap,"creationDeadline",creationDeadline);
        HelperFunctions.addIfNotNull(jsonMap,"initiationDeadline",initiationDeadline);
        return jsonMap;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public Integer getWeeks() {
        return weeks;
    }

    public Long getPreferenceDeadline() {
        return preferenceDeadline;
    }

    public Long getCreationDeadline() {
        return creationDeadline;
    }

    public Long getInitiationDeadline() {
        return initiationDeadline;
    }
}
