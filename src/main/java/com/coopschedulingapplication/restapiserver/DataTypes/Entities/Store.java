package com.coopschedulingapplication.restapiserver.DataTypes.Entities;

import com.coopschedulingapplication.restapiserver.DataTypes.HelperFunctions;

import java.util.HashMap;
import java.util.Map;

public class Store {
    private final Integer id;
    private final String address;
    private final String city;
    private final String key;

    public Store(Map<String,Object> json){
        this.id = (Integer) json.get("id");
        this.address = (String) json.get("address");
        this.city = (String) json.get("city");
        this.key = (String) json.get("key");
    }

    public Map<String, Object> toJson(){
        HashMap<String,Object> jsonMap = new HashMap<>();
        HelperFunctions.addIfNotNull(jsonMap,"id",id);
        HelperFunctions.addIfNotNull(jsonMap,"address",address);
        HelperFunctions.addIfNotNull(jsonMap,"city",city);
        HelperFunctions.addIfNotNull(jsonMap,"name",key);
        return jsonMap;
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
