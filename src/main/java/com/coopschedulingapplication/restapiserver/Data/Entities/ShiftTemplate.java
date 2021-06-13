package com.coopschedulingapplication.restapiserver.Data.Entities;

import com.coopschedulingapplication.restapiserver.Data.Enums.WeekDay;
import com.coopschedulingapplication.restapiserver.Data.Enums.WorkerType;

import java.util.Map;

public class ShiftTemplate {
    private final Integer id;
    private final Integer storeId;
    private final Long startTime;
    private final Long endTime;
    private WeekDay weekDay;
    private final WorkerType workerType;

    public ShiftTemplate(Integer id, Integer storeId, Long startTime, Long endTime, WeekDay weekDay, WorkerType workerType) {
        this.id = id;
        this.storeId = storeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.weekDay = weekDay;
        this.workerType = workerType;
    }

    public ShiftTemplate(Map<String, Object> json){
        this.id = (Integer) json.get("id");
        this.storeId = (Integer) json.get("storeId");
        this.startTime = (Long) json.get("startTime");
        this.endTime = (Long) json.get("endTime");
        this.weekDay = json.get("weekDay") != null ? WeekDay.valueOf((String) json.get("weekDay")) : null;
        this.workerType = json.get("workerType") != null ? WorkerType.valueOf((String) json.get("workerType")) : null;
    }

    public Map<String, Object> toJson(){
        return Map.of(
                "id",id,
                "storeId",storeId,
                "startTime",startTime,
                "endTime",endTime,
                "weekDay",weekDay,
                "workerType",workerType
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

    public void setWeekDay(WeekDay weekDay){
        this.weekDay = weekDay;
    }
}
