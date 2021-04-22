package com.coopschedulingapplication.restapiserver.persistence;

import com.coopschedulingapplication.restapiserver.ConnectionObjects.DepartmentCreationValues;
import com.coopschedulingapplication.restapiserver.ConnectionObjects.WorkerCreationValues;
import com.coopschedulingapplication.restapiserver.DataObjects.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.security.Principal;
import java.util.List;

public interface IPersistence {

    WorkerCreationRequest addWorkerCreationRequest(NamedParameterJdbcTemplate jdbcTemplate, WorkerCreationRequest request, Principal principal);
    WorkerCreationRequest acceptWorkerCreationRequest(NamedParameterJdbcTemplate jdbcTemplate, WorkerCreationRequest request);
    WorkerCreationRequest deleteWorkerCreationRequest(NamedParameterJdbcTemplate jdbcTemplate, WorkerCreationRequest request);

    ShiftTemplate addShiftTemplate(NamedParameterJdbcTemplate jdbcTemplate, ShiftTemplate template, Principal principal);
    ShiftTemplate updateShiftTemplate(NamedParameterJdbcTemplate jdbcTemplate, ShiftTemplate template);
    ShiftTemplate deleteShiftTemplate(NamedParameterJdbcTemplate jdbcTemplate, ShiftTemplate template);

    ScheduleTemplate setScheduleTemplate(NamedParameterJdbcTemplate jdbcTemplate, ScheduleTemplate template);
    SchedulePreferences setSchedulePreferences(NamedParameterJdbcTemplate jdbcTemplate, SchedulePreferences preferences);

    User getUser(NamedParameterJdbcTemplate jdbcTemplate, int userId);
    Store getUserStore(NamedParameterJdbcTemplate jdbcTemplate, int userId);
    List<ShiftTemplate> getStoreShiftTemplates(NamedParameterJdbcTemplate jdbcTemplate, int storeId);
    List<WorkerCreationRequest> getStoreWorkerCreationRequests(NamedParameterJdbcTemplate jdbcTemplate, int storeId);
    ScheduleTemplate getStoreScheduleTemplate(NamedParameterJdbcTemplate jdbcTemplate, int storeId);

    void addWorker(NamedParameterJdbcTemplate jdbcTemplate, WorkerCreationValues values);
    void addDepartment(NamedParameterJdbcTemplate jdbcTemplate, DepartmentCreationValues values);
}
