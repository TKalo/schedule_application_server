package com.coopschedulingapplication.restapiserver.ConnectionObjects;

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

    public static DepartmentCreationValues fromJson(Map<String,Object> json){
        return new DepartmentCreationValues(
                (String) json.get("name"),
                (String) json.get("email"),
                (String) json.get("password"),
                (String) json.get("address"),
                (String) json.get("city")
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

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }
}
