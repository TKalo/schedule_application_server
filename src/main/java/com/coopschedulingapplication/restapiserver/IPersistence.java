package com.coopschedulingapplication.restapiserver;

import com.coopschedulingapplication.restapiserver.DataObjects.ScheduleTemplate;
import com.coopschedulingapplication.restapiserver.DataObjects.ShiftTemplate;
import com.coopschedulingapplication.restapiserver.DataObjects.WorkerCreationRequest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.security.Principal;
import java.util.Map;

public interface IPersistence {

    Map<String, Object> addWorkerCreationRequest(NamedParameterJdbcTemplate jdbcTemplate, WorkerCreationRequest request, Principal principal);
    Map<String, Object> acceptWorkerCreationRequest(NamedParameterJdbcTemplate jdbcTemplate, WorkerCreationRequest request);
    Map<String, Object> deleteWorkerCreationRequest(NamedParameterJdbcTemplate jdbcTemplate, WorkerCreationRequest request);

    Map<String, Object> addShiftTemplate(NamedParameterJdbcTemplate jdbcTemplate, ShiftTemplate template, Principal principal);
    Map<String, Object> updateShiftTemplate(NamedParameterJdbcTemplate jdbcTemplate, ShiftTemplate template);
    Map<String, Object> deleteShiftTemplate(NamedParameterJdbcTemplate jdbcTemplate, ShiftTemplate template);

    Map<String, Object> setScheduleTemplate(NamedParameterJdbcTemplate jdbcTemplate, ScheduleTemplate template);

}
