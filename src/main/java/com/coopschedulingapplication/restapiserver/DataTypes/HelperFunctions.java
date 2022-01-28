package com.coopschedulingapplication.restapiserver.DataTypes;

import java.util.Map;

public class HelperFunctions {

    public static void addIfNotNull(Map<String, Object> map, String key, Object value){
        if(value != null) map.put(key,value);
    }
}
