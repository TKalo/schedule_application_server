package com.coopschedulingapplication.restapiserver.Data.Objects;

import java.util.Map;

public class Store {
    private final Integer id;
    private final String address;
    private final String city;
    private final String key;

    public Store(Integer id, String address, String city, String key) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.key = key;
    }

    public static Store fromJson(Map<String,Object> json){
        return new Store(
                (Integer) json.get("id"),
                (String) json.get("address"),
                (String) json.get("city"),
                (String) json.get("key")
        );
    }

    public Integer getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getKey() {
        return key;
    }
}
