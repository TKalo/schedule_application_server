package com.coopschedulingapplication.restapiserver.DataObjects;

import java.util.Map;

public class WorkerCreationRequest {
    Integer id;
    WorkerType type;
    WorkerCreationStatus status;
    String key;

    public WorkerCreationRequest(Integer id, WorkerType type, WorkerCreationStatus status, String key) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.key = key;
    }


    public static WorkerCreationRequest fromJson(Map<String, Object> json){
        return new WorkerCreationRequest(
                (Integer) json.get("id"),
                (WorkerType) json.get("type"),
                (WorkerCreationStatus) json.get("status"),
                (String) json.get("key")
        );
    }

    public int getId() {
        return id;
    }

    public WorkerType getType() {
        return type;
    }

    public WorkerCreationStatus getStatus() {
        return status;
    }

    public String getKey() {
        return key;
    }
}
