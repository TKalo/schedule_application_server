package com.coopschedulingapplication.restapiserver.Data.Entities;

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

    public Store(Map<String,Object> json){
        this.id = (Integer) json.get("id");
        this.address = (String) json.get("address");
        this.city = (String) json.get("city");
        this.key = (String) json.get("key");
    }

    public Map<String, Object> toJson(){
        return Map.of(
                "id",id,
                "address",address,
                "city",city,
                "key",key
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
