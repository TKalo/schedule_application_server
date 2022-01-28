package com.coopschedulingapplication.restapiserver.Data.ValueEntities;

import java.util.Map;

public class DepartmentCreationValues {

    private final String name;
    private final String email;
    private final String password;
    private final String address;
    private final String city;

    public DepartmentCreationValues(String name, String email, String password, String address, String city) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.city = city;
    }

    public DepartmentCreationValues(Map<String,Object> json){
        this.name = (String) json.get("name");
        this.email = (String) json.get("email");
        this.password = (String) json.get("password");
        this.address = (String) json.get("address");
        this.city = (String) json.get("city");
    }

    public Map<String, Object> toJson(){
        return Map.of(
                "name",name,
                "email",email,
                "password",password,
                "address",address,
                "city",city
        );
    }
}
