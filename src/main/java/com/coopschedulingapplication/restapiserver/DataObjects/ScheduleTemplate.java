package com.coopschedulingapplication.restapiserver.DataObjects;

import java.math.BigInteger;
import java.util.Map;

public class ScheduleTemplate {
    Integer storeId;
    Integer weeks;
    BigInteger preferenceDeadline;
    BigInteger creationDeadline;
    BigInteger initiationDeadline;

    public ScheduleTemplate(Integer storeId, Integer weeks, BigInteger preferenceDeadline, BigInteger creationDeadline, BigInteger initiationDeadline) {
        this.storeId = storeId;
        this.weeks = weeks;
        this.preferenceDeadline = preferenceDeadline;
        this.creationDeadline = creationDeadline;
        this.initiationDeadline = initiationDeadline;
    }

    public static ScheduleTemplate fromJson(Map<String, Object> json){
        return new ScheduleTemplate(
                (Integer) json.get("id"),
                (Integer) json.get("weeks"),
                (BigInteger) json.get("preferenceDeadline"),
                (BigInteger) json.get("creationDeadline"),
                (BigInteger) json.get("initiationDeadline")
        );
    }

    public Integer getStoreId() {
        return storeId;
    }

    public Integer getWeeks() {
        return weeks;
    }

    public BigInteger getPreferenceDeadline() {
        return preferenceDeadline;
    }

    public BigInteger getCreationDeadline() {
        return creationDeadline;
    }

    public BigInteger getInitiationDeadline() {
        return initiationDeadline;
    }
}
