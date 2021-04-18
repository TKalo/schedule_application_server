package com.coopschedulingapplication.restapiserver.Configurations;

import com.coopschedulingapplication.restapiserver.StompEntities.UserType;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Component
public class WebsocketAuthenticationService {

    NamedParameterJdbcTemplate jdbcTemplate;

    WebsocketAuthenticationService(NamedParameterJdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private UsernamePasswordAuthenticationToken token;

    // This method MUST return a UsernamePasswordAuthenticationToken instance, the spring security chain is testing it with 'instanceof' later on. So don't use a subclass of it or any other class
    public String getAuthenticated(final String  email, final String password, final  String rawUserType) {

        try {
            UserType userType;
            try{userType = UserType.valueOf(rawUserType);} catch (Exception e){ return "unknown usertype";};

            if(email == null || password == null) return "email or password is null";

            Map<String, Object> rawUser;
            try{rawUser = jdbcTemplate.queryForMap("SELECT * FROM user_table WHERE LOWER(email)=LOWER(:email)", Map.of("email",email));}catch (Exception e){
                return "email does not match any users";
            }

            if(rawUser.isEmpty()) return "email does not match any users";

            String foundPassword = (String) rawUser.get("password");
            UserType foundUserType = UserType.valueOf((String) rawUser.get("type"));

            if(!foundPassword.equals(password)) return "password does not match user";

            if(!foundUserType.equals(userType)) return "user does now have sufficient authority";

            // null credentials, we do not pass the password along
            Set<GrantedAuthority> authorities = Collections.singleton((GrantedAuthority) userType::toString);

            token = new UsernamePasswordAuthenticationToken(rawUser.get("id"), null, authorities);

            return null;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    public UsernamePasswordAuthenticationToken getToken() {
        return token;
    }
}
