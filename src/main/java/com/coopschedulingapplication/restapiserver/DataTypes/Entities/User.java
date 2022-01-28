package com.coopschedulingapplication.restapiserver.DataTypes.Entities;

import com.coopschedulingapplication.restapiserver.DataTypes.Enums.UserType;
import com.coopschedulingapplication.restapiserver.DataTypes.HelperFunctions;

import java.util.HashMap;
import java.util.Map;

public class User {
    private final Integer id;
    private final Integer storeId;
    private final UserType type;
    private final String name;
    private final String email;

    public User(Map<String, Object> json){
        this.id = (Integer) json.get("id");
        this.storeId = (Integer) json.get("storeId");
        this.type = json.get("type") != null ? UserType.valueOf((String) json.get("type")) : null;
        this.name = (String) json.get("name");
        this.email = (String) json.get("email");
    }

    public Map<String, Object> toJson(){
        HashMap<String,Object> jsonMap = new HashMap<>();
        HelperFunctions.addIfNotNull(jsonMap,"id",id);
        HelperFunctions.addIfNotNull(jsonMap,"storeId",storeId);
        HelperFunctions.addIfNotNull(jsonMap,"type",type);
        HelperFunctions.addIfNotNull(jsonMap,"name",name);
        HelperFunctions.addIfNotNull(jsonMap,"email",email);
        return jsonMap;
    }

    public Integer getId() {
        return id;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public UserType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
