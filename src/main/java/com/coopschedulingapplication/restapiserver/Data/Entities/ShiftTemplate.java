package com.coopschedulingapplication.restapiserver.Data.Entities;

import com.coopschedulingapplication.restapiserver.Data.Enums.WeekDay;
import com.coopschedulingapplication.restapiserver.Data.Enums.WorkerType;

import java.util.HashMap;
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
        HashMap<String,Object> jsonMap = new HashMap<>();
        if(id != null) jsonMap.put("id",id);
        if(storeId != null) jsonMap.put("storeId",storeId);
        if(startTime != null) jsonMap.put("startTime",startTime);
        if(endTime != null) jsonMap.put("endTime",endTime);
        if(weekDay != null) jsonMap.put("weekDay",weekDay);
        if(workerType != null) jsonMap.put("workerType",workerType);
        return jsonMap;
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
