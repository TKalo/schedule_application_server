package com.coopschedulingapplication.restapiserver;

import com.coopschedulingapplication.restapiserver.DataObjects.ScheduleTemplate;
import com.coopschedulingapplication.restapiserver.DataObjects.ShiftTemplate;
import com.coopschedulingapplication.restapiserver.DataObjects.WorkerCreationRequest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.security.Principal;

public interface IPersistence {

    WorkerCreationRequest addWorkerCreationRequest(NamedParameterJdbcTemplate jdbcTemplate, WorkerCreationRequest request, Principal principal);
    WorkerCreationRequest acceptWorkerCreationRequest(NamedParameterJdbcTemplate jdbcTemplate, WorkerCreationRequest request);
    WorkerCreationRequest deleteWorkerCreationRequest(NamedParameterJdbcTemplate jdbcTemplate, WorkerCreationRequest request);

    ShiftTemplate addShiftTemplate(NamedParameterJdbcTemplate jdbcTemplate, ShiftTemplate template, Principal principal);
    ShiftTemplate updateShiftTemplate(NamedParameterJdbcTemplate jdbcTemplate, ShiftTemplate template);
    ShiftTemplate deleteShiftTemplate(NamedParameterJdbcTemplate jdbcTemplate, ShiftTemplate template);

    ScheduleTemplate setScheduleTemplate(NamedParameterJdbcTemplate jdbcTemplate, ScheduleTemplate template);

}
