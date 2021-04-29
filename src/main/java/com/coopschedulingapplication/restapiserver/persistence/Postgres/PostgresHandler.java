package com.coopschedulingapplication.restapiserver.persistence.Postgres;

import com.coopschedulingapplication.restapiserver.Data.Entities.*;
import com.coopschedulingapplication.restapiserver.Data.ValueEntities.DepartmentCreationValues;
import com.coopschedulingapplication.restapiserver.Data.ValueEntities.WorkerCreationValues;
import com.coopschedulingapplication.restapiserver.persistence.IPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;
import java.util.Map;


@Component
public class PostgresHandler implements IPersistence {


    NamedParameterJdbcTemplate jdbcTemplate;

    WorkerCreationFunctions workerCreation = new WorkerCreationFunctions();
    ShiftTemplateFunctions shiftTemplate = new ShiftTemplateFunctions();
    ScheduleTemplateFunctions scheduleTemplate = new ScheduleTemplateFunctions();
    SchedulePreferencesFunctions schedulePreferences = new SchedulePreferencesFunctions();

    @Autowired
    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public WorkerCreationRequest addWorkerCreationRequest(WorkerCreationRequest request, Principal principal) {
        return workerCreation.add(request, principal);
    }

    @Override
    public WorkerCreationRequest acceptWorkerCreationRequest(WorkerCreationRequest request) {
        return workerCreation.accept(request);
    }

    @Override
    public WorkerCreationRequest deleteWorkerCreationRequest(WorkerCreationRequest request) {
        return workerCreation.delete(request);
    }

    @Override
    public List<WorkerCreationRequest> getWorkerCreationRequestsStore(int storeId) {
        return workerCreation.getByStore(storeId);
    }

    @Override
    public ShiftTemplate addShiftTemplate(ShiftTemplate template, Principal principal) {
        return shiftTemplate.add(template, principal);
    }

    @Override
    public ShiftTemplate updateShiftTemplate(ShiftTemplate template) {
        return shiftTemplate.update(template);
    }

    @Override
    public ShiftTemplate deleteShiftTemplate(ShiftTemplate template) {
        return shiftTemplate.delete(template);
    }

    @Override
    public List<ShiftTemplate> getShiftTemplatesByStore(int storeId) {
        return shiftTemplate.getByStore(storeId);
    }

    @Override
    public ScheduleTemplate setScheduleTemplate(ScheduleTemplate template) {
        return scheduleTemplate.set(template);
    }

    @Override
    public ScheduleTemplate getScheduleTemplateStore(int storeId) {
        return scheduleTemplate.getByStore(storeId);
    }

    @Override
    public SchedulePreferences setSchedulePreferences(SchedulePreferences preferences) {
        return schedulePreferences.set(preferences);
    }

    @Override
    public List<SchedulePreferences> getSchedulePreferencesByStore(int storeId) {
        return schedulePreferences.getByStore(storeId);
    }

    @Override
    public SchedulePreferences getSchedulePreferencesByUser(int userId) {
        return schedulePreferences.getByUser(userId);
    }

    @Override
    public User getUser(int userId) {
        return HelperFunctions.successOrNull(()-> {
            Map<String, Object> sqlMap = jdbcTemplate.queryForMap("SELECT * FROM user_table WHERE id=:id", Map.of("id",userId));
            Map<String, Object> parsableMap = HelperFunctions.snake2Camel(sqlMap);
            return User.fromJson(parsableMap);
        });
    }

    @Override
    public Store getUserStore(int userId) {
        return HelperFunctions.successOrNull(()-> {
            Map<String, Object> sqlMap = jdbcTemplate.queryForMap("SELECT * FROM store WHERE id=(SELECT store_id FROM user_table WHERE id=:userId)", Map.of("user_id", userId));
            Map<String, Object> parsableMap = HelperFunctions.snake2Camel(sqlMap);
            return Store.fromJson(parsableMap);
        });
    }

    @Override
    public void addWorker(WorkerCreationValues values) {
        jdbcTemplate.queryForMap(
                "WITH " +
                        "request_values(temp_store_id, temp_user_type) AS ((SELECT store_id,type FROM worker_creation_request WHERE key = :key))," +
                        "temp_user_id(id) AS (INSERT INTO user_table (name, email, password, store_id, type) VALUES(:name, :email, :password, (SELECT temp_store_id FROM request_values), (SELECT temp_user_type FROM request_values)) RETURNING id)" +
                        "INSERT INTO schedule_preferences (user_id) VALUES((SELECT id FROM temp_user_id));", HelperFunctions.map2SqlMap(values.toJson()));
    }

    @Override
    public void addDepartment(DepartmentCreationValues values) {
        jdbcTemplate.update("INSERT INTO user_table (name, email, password, store_id, type) VALUES(:name, :email, :password, (INSERT INTO store (address, city) VALUES(:address, :city) RETURNING id), 'administrator')", HelperFunctions.map2SqlMap(values.toJson()));
    }
}


