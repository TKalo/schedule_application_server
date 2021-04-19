package com.coopschedulingapplication.restapiserver.DataObjects;

import java.util.Map;

public class User {
    private Integer id;
    private Integer storeId;
    private UserType type;
    private String name;
    private String email;

    public User(Integer id, Integer storeId, UserType type, String name, String email) {
        this.id = id;
        this.storeId = storeId;
        this.type = type;
        this.name = name;
        this.email = email;
    }

    public static User fromJson(Map<String, Object> json){
        return new User(
                (Integer) json.get("id"),
                (Integer) json.get("storeId"),
                (UserType) json.get("type"),
                (String) json.get("name"),
                (String) json.get("email")
        );
    }

    public int getId() {
        return id;
    }

    public int getStoreId() {
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
