package com.coopschedulingapplication.restapiserver.persistence.Postgres;

import com.coopschedulingapplication.restapiserver.Data.ValueEntities.PersistenceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserAuthentication {

    private PostgresGenericFunctions PGF;
    @Autowired
    private void setPostgresGenericFunctions(PostgresGenericFunctions PGF) {
        this.PGF = PGF;
    }

    public PersistenceResult<Integer> authenticate(String email, String password, String userType){
        String getUserSQL = "SELECT id FROM user_values WHERE email = :email AND password = :password AND (SELECT name FROM roles WHERE id = (SELECT role_id FROM user_roles WHERE user_id = user_values.id)) = :user_type;";
        String checkEmailSQL = "SELECT EXISTS(SELECT * FROM user_values WHERE email = :email) AS email_exist;";
        String checkPasswordSQL = "SELECT EXISTS(SELECT * FROM user_values WHERE email = :email AND password = :password) AS email_exist;";

        Map<String, Object> params = new HashMap<>();
        params.put("email",email);
        params.put("password",password);
        params.put("user_type",userType);
        Integer userId = HelperFunctions.successOrNull(()-> (Integer) PGF.queryMap(getUserSQL,params).get("id"));

        if(userId != null) return new PersistenceResult<>(userId,null);

         //find reason for failure
        Boolean emailExist = HelperFunctions.successOrNull(()-> (Boolean) PGF.queryMap(checkEmailSQL,params).get("emailExist"));

        if(emailExist == null) return new PersistenceResult<>(null,"internal network error");

        if(!emailExist) return new PersistenceResult<>(null,"email does not exist");

        Boolean passwordMatches = HelperFunctions.successOrNull(()-> (Boolean) PGF.queryMap(checkPasswordSQL,params).get("emailExist"));

        if(passwordMatches == null) return new PersistenceResult<>(null,"internal network error");

        if(!passwordMatches) return new PersistenceResult<>(null,"password does not match email");

        return new PersistenceResult<>(null,"user has insufficient access");
    }
}
