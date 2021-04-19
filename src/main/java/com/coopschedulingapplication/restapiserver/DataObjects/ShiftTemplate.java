package com.coopschedulingapplication.restapiserver.DataObjects;

import java.math.BigInteger;
import java.util.Map;

public class ShiftTemplate {
    Integer id;
    Integer storeId;
    BigInteger startTime;
    BigInteger endTime;
    WeekDay weekDay;
    WorkerType workerType;

    public ShiftTemplate(Integer id, Integer storeId, BigInteger startTime, BigInteger endTime, WeekDay weekDay, WorkerType workerType) {
        this.id = id;
        this.storeId = storeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.weekDay = weekDay;
        this.workerType = workerType;
    }

    public static ShiftTemplate fromJson(Map<String, Object> json){
        return new ShiftTemplate(
                (Integer) json.get("id"),
                (Integer) json.get("storeId"),
                (BigInteger) json.get("startTime"),
                (BigInteger) json.get("endTime"),
                json.get("weekDay") != null ? WeekDay.valueOf((String) json.get("weekDay")) : null,
                json.get("workerType") != null ? WorkerType.valueOf((String) json.get("workerType")) : null
        );
    }

    public Integer getId() {
        return id;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public BigInteger getStartTime() {
        return startTime;
    }

    public BigInteger getEndTime() {
        return endTime;
    }

    public WeekDay getWeekDay() {
        return weekDay;
    }

    public WorkerType getWorkerType() {
        return workerType;
    }
}
