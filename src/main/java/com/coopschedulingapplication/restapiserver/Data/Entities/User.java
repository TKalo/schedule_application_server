package com.coopschedulingapplication.restapiserver.Data.Entities;

import com.coopschedulingapplication.restapiserver.Data.Enums.UserType;

import java.util.Map;

public class User {
    private final Integer id;
    private final Integer storeId;
    private final UserType type;
    private final String name;
    private final String email;

    public User(Integer id, Integer storeId, UserType type, String name, String email) {
        this.id = id;
        this.storeId = storeId;
        this.type = type;
        this.name = name;
        this.email = email;
    }

    public User(Map<String, Object> json){
        this.id = (Integer) json.get("id");
        this.storeId = (Integer) json.get("storeId");
        this.type = json.get("type") != null ? UserType.valueOf((String) json.get("type")) : null;
        this.name = (String) json.get("name");
        this.email = (String) json.get("email");
    }

    public Map<String, Object> toJson(){
        return Map.of(
                "id",id,
                "storeId",storeId,
                "type",type,
                "name",name,
                "email",email
                );
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
