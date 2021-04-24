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
    List<WorkerCreationRequest> getWorkerCreationRequestsStore(NamedParameterJdbcTemplate jdbcTemplate, int storeId);

    ShiftTemplate addShiftTemplate(NamedParameterJdbcTemplate jdbcTemplate, ShiftTemplate template, Principal principal);
    ShiftTemplate updateShiftTemplate(NamedParameterJdbcTemplate jdbcTemplate, ShiftTemplate template);
    ShiftTemplate deleteShiftTemplate(NamedParameterJdbcTemplate jdbcTemplate, ShiftTemplate template);
    List<ShiftTemplate> getShiftTemplatesStore(NamedParameterJdbcTemplate jdbcTemplate, int storeId);

    ScheduleTemplate setScheduleTemplate(NamedParameterJdbcTemplate jdbcTemplate, ScheduleTemplate template);
    ScheduleTemplate getScheduleTemplateStore(NamedParameterJdbcTemplate jdbcTemplate, int storeId);

    SchedulePreferences setSchedulePreferences(NamedParameterJdbcTemplate jdbcTemplate, SchedulePreferences preferences);
    List<SchedulePreferences> getSchedulePreferencesStore(NamedParameterJdbcTemplate jdbcTemplate, int userId);
    SchedulePreferences getSchedulePreferencesUser(NamedParameterJdbcTemplate jdbcTemplate, int userId);

    User getUser(NamedParameterJdbcTemplate jdbcTemplate, int userId);
    Store getUserStore(NamedParameterJdbcTemplate jdbcTemplate, int userId);

    void addWorker(NamedParameterJdbcTemplate jdbcTemplate, WorkerCreationValues values);
    void addDepartment(NamedParameterJdbcTemplate jdbcTemplate, DepartmentCreationValues values);
}
