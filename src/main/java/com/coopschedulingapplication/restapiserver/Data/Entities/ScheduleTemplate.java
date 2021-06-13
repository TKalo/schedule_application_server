package com.coopschedulingapplication.restapiserver.Data.Entities;

import java.util.Map;

public class ScheduleTemplate {
    private final Integer storeId;
    private final Integer weeks;
    private final Long preferenceDeadline;
    private final Long creationDeadline;
    private final Long initiationDeadline;

    public ScheduleTemplate(Integer storeId, Integer weeks, Long preferenceDeadline, Long creationDeadline, Long initiationDeadline) {
        this.storeId = storeId;
        this.weeks = weeks;
        this.preferenceDeadline = preferenceDeadline;
        this.creationDeadline = creationDeadline;
        this.initiationDeadline = initiationDeadline;
    }

    public ScheduleTemplate(Map<String, Object> json){
        this.storeId = (Integer) json.get("storeId");
        this.weeks = (Integer) json.get("weeks");
        this.preferenceDeadline = (Long) json.get("preferenceDeadline");
        this.creationDeadline = (Long) json.get("creationDeadline");
        this.initiationDeadline = (Long) json.get("initiationDeadline");
    }

    public Map<String, Object> toJson(){
        return Map.of(
                "storeId",storeId,
                "weeks",weeks,
                "preferenceDeadline",preferenceDeadline,
                "creationDeadline",creationDeadline,
                "initiationDeadline",initiationDeadline
        );
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
