package com.coopschedulingapplication.restapiserver;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.security.Principal;
import java.util.Map;

public interface IPersistence {

    Map<String, Object> addWorkerCreationRequest(NamedParameterJdbcTemplate jdbcTemplate, Map<String, Object> params, Principal principal);
    Map<String, Object> acceptWorkerCreationRequest(NamedParameterJdbcTemplate jdbcTemplate, Map<String, Object> params);
    Map<String, Object> deleteWorkerCreationRequest(NamedParameterJdbcTemplate jdbcTemplate, Map<String, Object> params);

    Map<String, Object> addShiftTemplate(NamedParameterJdbcTemplate jdbcTemplate, Map<String, Object> params, Principal principal);
    Map<String, Object> updateShiftTemplate(NamedParameterJdbcTemplate jdbcTemplate, Map<String, Object> params);
    Map<String, Object> deleteShiftTemplate(NamedParameterJdbcTemplate jdbcTemplate, Map<String, Object> params);

    Map<String, Object> setScheduleTemplate(NamedParameterJdbcTemplate jdbcTemplate, Map<String, Object> params);

}
