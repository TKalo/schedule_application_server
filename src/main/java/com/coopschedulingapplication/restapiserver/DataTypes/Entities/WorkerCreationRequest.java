package com.coopschedulingapplication.restapiserver.Data.Entities;

import com.coopschedulingapplication.restapiserver.Data.Enums.WorkerCreationStatus;
import com.coopschedulingapplication.restapiserver.Data.Enums.WorkerType;
import com.coopschedulingapplication.restapiserver.Data.HelperFunctions;

import java.util.HashMap;
import java.util.Map;

public class WorkerCreationRequest {
    private final Integer id;
    private final Integer storeId;
    private final WorkerType type;
    private final WorkerCreationStatus status;
    private final String key;

    public WorkerCreationRequest(Map<String, Object> json){
        this.id = (Integer) json.get("id");
        this.storeId = (Integer) json.get("storeId");
        this.type = json.get("type") != null ? WorkerType.valueOf((String) json.get("type")) : null;
        this.status = json.get("status") != null ? WorkerCreationStatus.valueOf((String) json.get("status")) : null;
        this.key = (String) json.get("key");
    }


    public Map<String, Object> toJson(){
        HashMap<String,Object> jsonMap = new HashMap<>();
        HelperFunctions.addIfNotNull(jsonMap,"id",id);
        HelperFunctions.addIfNotNull(jsonMap,"storeId",storeId);
        HelperFunctions.addIfNotNull(jsonMap,"type",type);
        HelperFunctions.addIfNotNull(jsonMap,"status",status);
        HelperFunctions.addIfNotNull(jsonMap,"key",key);
        return jsonMap;
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
