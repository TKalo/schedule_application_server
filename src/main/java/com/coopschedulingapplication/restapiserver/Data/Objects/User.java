package com.coopschedulingapplication.restapiserver.Data.Objects;

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

    public static User fromJson(Map<String, Object> json){
        return new User(
                (Integer) json.get("id"),
                (Integer) json.get("storeId"),
                json.get("type") != null ? UserType.valueOf((String) json.get("type")) : null,
                (String) json.get("name"),
                (String) json.get("email")
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
