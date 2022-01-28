package com.coopschedulingapplication.restapiserver.DataTypes.ValueEntities;

import java.util.Map;

public class WorkerCreationValues {
    private final String name;
    private final String email;
    private final String password;
    private final String key;

    public WorkerCreationValues(String name, String email, String password, String key) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.key = key;
    }

    public WorkerCreationValues(Map<String, Object> json){
        this.name = (String) json.get("name");
        this.email = (String) json.get("email");
        this.password = (String) json.get("password");
        this.key = (String) json.get("key");
    }

    public Map<String, Object> toJson(){
        return Map.of(
                "name",name,
                "email",email,
                "password",password,
                "key",key
        );
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getKey() {
        return key;
    }
}
