package com.coopschedulingapplication.restapiserver.Data.Objects;

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


    public static WorkerCreationRequest fromJson(Map<String, Object> json){
        return new WorkerCreationRequest(
                (Integer) json.get("id"),
                (Integer) json.get("storeId"),
                json.get("type") != null ? WorkerType.valueOf((String) json.get("type")) : null,
                json.get("status") != null ? WorkerCreationStatus.valueOf((String) json.get("status")) : null,
                (String) json.get("key")
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
