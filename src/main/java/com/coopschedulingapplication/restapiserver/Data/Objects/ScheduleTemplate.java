package com.coopschedulingapplication.restapiserver.Data.Objects;

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

    public static ScheduleTemplate fromJson(Map<String, Object> json){
        return new ScheduleTemplate(
                (Integer) json.get("storeId"),
                (Integer) json.get("weeks"),
                (Long) json.get("preferenceDeadline"),
                (Long) json.get("creationDeadline"),
                (Long) json.get("initiationDeadline")
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