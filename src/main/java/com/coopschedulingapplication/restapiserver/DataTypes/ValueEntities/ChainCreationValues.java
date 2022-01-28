package com.coopschedulingapplication.restapiserver.Data.ValueEntities;

import java.util.Map;

public class ChainCreationValues {

    private final String name;
    private final String email;
    private final String password;
    private final String chainName;

    public ChainCreationValues(String name, String email, String password, String chainName) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.chainName = chainName;
    }

    public ChainCreationValues(Map<String,Object> json){
        this.name = (String) json.get("name");
        this.email = (String) json.get("email");
        this.password = (String) json.get("password");
        this.chainName = (String) json.get("chainName");
    }

    public Map<String, Object> toJson(){
        return Map.of(
                "name",name,
                "email",email,
                "password",password,
                "address",chainName
        );
    }
}
