package com.coopschedulingapplication.restapiserver.Data.Objects;

import com.coopschedulingapplication.restapiserver.Data.Enums.WeekDay;
import com.coopschedulingapplication.restapiserver.Data.Enums.WorkerType;

import java.util.Map;

public class ShiftTemplate {
    private final Integer id;
    private final Integer storeId;
    private final Long startTime;
    private final Long endTime;
    private final WeekDay weekDay;
    private final WorkerType workerType;

    public ShiftTemplate(Integer id, Integer storeId, Long startTime, Long endTime, WeekDay weekDay, WorkerType workerType) {
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
                (Long) json.get("startTime"),
                (Long) json.get("endTime"),
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

    public Long getStartTime() {
        return startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public WeekDay getWeekDay() {
        return weekDay;
    }

    public WorkerType getWorkerType() {
        return workerType;
    }
}
