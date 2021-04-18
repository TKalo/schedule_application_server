package com.coopschedulingapplication.restapiserver.Configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableGlobalAuthentication
public class HttpSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // This is not for websocket authorization, and this should most likely not be altered.
        http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/schedule-application-websocket").permitAll().and()
                .authorizeRequests().antMatchers("/addDepartment").permitAll().and()
                .authorizeRequests().antMatchers("/addUserCreationRequest").permitAll()
                .anyRequest().permitAll();
    }

}
