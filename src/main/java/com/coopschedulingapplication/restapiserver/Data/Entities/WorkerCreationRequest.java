package com.coopschedulingapplication.restapiserver.Data.Entities;

import com.coopschedulingapplication.restapiserver.Data.Enums.WorkerCreationStatus;
import com.coopschedulingapplication.restapiserver.Data.Enums.WorkerType;

import java.util.Map;

public class WorkerCreationRequest {
    private final Integer id;
    private final Integer storeId;
    private final WorkerType type;
    private final WorkerCreationStatus status;
    private final String key;

    public WorkerCreationRequest(Integer id, Integer storeId, WorkerType type, WorkerCreationStatus status, String key) {
        this.id = id;
        this.storeId = storeId;
        this.type = type;
        this.status = status;
        this.key = key;
    }

    public WorkerCreationRequest(Map<String, Object> json){
        this.id = (Integer) json.get("id");
        this.storeId = (Integer) json.get("storeId");
        this.type = json.get("type") != null ? WorkerType.valueOf((String) json.get("type")) : null;
        this.status = json.get("status") != null ? WorkerCreationStatus.valueOf((String) json.get("status")) : null;
        this.key = (String) json.get("key");
    }

    public Map<String, Object> toJson(){
        return Map.of(
                "id",id,
                "storeId",storeId,
                "type",type,
                "status",status,
                "key",key
        );
    }

    public Integer getId() {
        return id;
    }

    public Integer getStoreId() {
        return storeId;
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
