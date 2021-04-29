package com.coopschedulingapplication.restapiserver.persistence;

import com.coopschedulingapplication.restapiserver.Data.ValueEntities.DepartmentCreationValues;
import com.coopschedulingapplication.restapiserver.Data.ValueEntities.WorkerCreationValues;
import com.coopschedulingapplication.restapiserver.Data.Entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class PostgresHandler implements IPersistence {


    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public WorkerCreationRequest addWorkerCreationRequest(WorkerCreationRequest request, Principal principal) {
        return successOrNull(()->{
            Map<String, Object> sqlMap = jdbcTemplate.queryForMap("INSERT INTO worker_creation_request AS wcr (type, store_id) VALUES(:type, (SELECT store_id FROM user_table WHERE id=:userId)) RETURNING *", map2SqlMap(request.toJson()));
            Map<String, Object> parsableMap = snake2Camel(sqlMap);
            return WorkerCreationRequest.fromJson(snake2Camel(parsableMap));
        });
    }

    @Override
    public WorkerCreationRequest acceptWorkerCreationRequest(WorkerCreationRequest request) {
        return successOrNull(()-> {
            Map<String, Object> sqlMap = jdbcTemplate.queryForMap("UPDATE worker_creation_request SET status = :accepted WHERE id = :id RETURNING *", map2SqlMap(request.toJson()));
            Map<String, Object> parsableMap = snake2Camel(sqlMap);
            return WorkerCreationRequest.fromJson(parsableMap);
        });
    }

    @Override
    public WorkerCreationRequest deleteWorkerCreationRequest(WorkerCreationRequest request) {
        return successOrNull(()-> {
            Map<String, Object> sqlMap = jdbcTemplate.queryForMap("WITH t1 AS (DELETE FROM worker_creation_request WHERE id = :id RETURNING *) SELECT * FROM t1",map2SqlMap(request.toJson()));
            Map<String, Object> parsableMap = snake2Camel(sqlMap);
            return WorkerCreationRequest.fromJson(parsableMap);
        });
    }

    @Override
    public List<WorkerCreationRequest> getWorkerCreationRequestsStore(int storeId) {
        return successOrNull(()-> {
            List<Map<String, Object>> sqlMapList = jdbcTemplate.queryForList("SELECT * FROM worker_creation_request WHERE store_id=:storeId", Map.of("store_id", storeId));
            return sqlMapList.stream().map(sqlMap -> {
                Map<String, Object> parsableMap = snake2Camel(sqlMap);
                return WorkerCreationRequest.fromJson(parsableMap);
            }).collect(Collectors.toList());
        });
    }

    @Override
    public ShiftTemplate addShiftTemplate(ShiftTemplate template, Principal principal) {
        return successOrNull(()-> {
            Map<String, Object> json = template.toJson();
            System.out.println(json);
            MapSqlParameterSource sqlInputMap = map2SqlMap(json);
            sqlInputMap.addValue("userId", Integer.parseInt(principal.getName()));
            Map<String, Object> sqlOutputMap = jdbcTemplate.queryForMap("INSERT INTO shift_template (week_day, start_time, end_time, store_id, worker_type) VALUES(:weekDay, :startTime, :endTime, (SELECT store_id FROM user_table WHERE id = :userId), :workerType) RETURNING *", sqlInputMap);
            System.out.println("successOrNull");
            Map<String, Object> parsableMap = snake2Camel(sqlOutputMap);
            System.out.println(parsableMap);
            System.out.println("successOrNull");
            return ShiftTemplate.fromJson(parsableMap);
        });
    }

    @Override
    public ShiftTemplate updateShiftTemplate(ShiftTemplate template) {
        return successOrNull(()-> {
            Map<String, Object> sqlMap = jdbcTemplate.queryForMap("UPDATE shift_template SET week_day = :weekDay, start_time = :startTime, end_time = :endTime, worker_type = :workerType WHERE id = :id RETURNING *", map2SqlMap(template.toJson()));
            Map<String, Object> parsableMap = snake2Camel(sqlMap);
            return ShiftTemplate.fromJson(parsableMap);
        });
    }

    @Override
    public ShiftTemplate deleteShiftTemplate(ShiftTemplate template) {
        return successOrNull(()-> {
            Map<String, Object> sqlMap = jdbcTemplate.queryForMap("WITH t1 AS (DELETE FROM shift_template WHERE id = :id RETURNING *) SELECT * FROM t1", map2SqlMap(template.toJson()));
            Map<String, Object> parsableMap = snake2Camel(sqlMap);
            return ShiftTemplate.fromJson(parsableMap);
        });
    }

    @Override
    public List<ShiftTemplate> getShiftTemplatesStore(int storeId) {
        return successOrNull(()-> {
            List<Map<String, Object>> sqlMapList = jdbcTemplate.queryForList("SELECT * FROM shift_template WHERE store_id=:storeId", Map.of("storeId", storeId));
            return sqlMapList.stream().map(sqlMap -> {
                Map<String, Object> parsableMap = snake2Camel(sqlMap);
                return ShiftTemplate.fromJson(parsableMap);
            }).collect(Collectors.toList());
        });
    }

    @Override
    public ScheduleTemplate setScheduleTemplate(ScheduleTemplate template) {
        return successOrNull(()-> {
            Map<String, Object> sqlMap = jdbcTemplate.queryForMap("UPDATE schedule_template SET preference_deadline = :preferenceDeadline, creation_deadline=:creationDeadline, initiation_deadline=:initiationDeadline, weeks=:weeks WHERE store_id = :storeId RETURNING *",map2SqlMap(template.toJson()));
            Map<String, Object> parsableMap = snake2Camel(sqlMap);
            return ScheduleTemplate.fromJson(parsableMap);
        });
    }

    @Override
    public ScheduleTemplate getScheduleTemplateStore(int storeId) {
        return successOrNull(()-> {
            Map<String, Object> sqlMap = jdbcTemplate.queryForMap("SELECT * FROM schedule_template WHERE store_id=:storeId",Map.of("store_id",storeId));
            Map<String, Object> parsableMap = snake2Camel(sqlMap);
            return ScheduleTemplate.fromJson(parsableMap);
        });
    }

    @Override
    public SchedulePreferences setSchedulePreferences(SchedulePreferences preferences) {
        return successOrNull(()-> {
            Map<String, Object> sqlMap = jdbcTemplate.queryForMap("UPDATE schedule_preferences SET pref_week_days = :prefWeekDays, max_week_days=:maxWeekDays WHERE user_id = :userId RETURNING *",map2SqlMap(preferences.toJson()));
            Map<String, Object> parsableMap = snake2Camel(sqlMap);
            return SchedulePreferences.fromJson(parsableMap);
        });
    }

    @Override
    public List<SchedulePreferences> getSchedulePreferencesStore(int storeId) {
        return successOrNull(()-> {
            List<Map<String, Object>> sqlMapList = jdbcTemplate.queryForList("SELECT * FROM schedule_preferences WHERE user_id IN (SELECT user_id FROM user_table WHERE store_id = :storeId)",Map.of("store_id",storeId));
            return sqlMapList.stream().map(sqlMap -> {
                Map<String, Object> parsableMap = snake2Camel(sqlMap);
                return SchedulePreferences.fromJson(parsableMap);
            }).collect(Collectors.toList());
        });
    }

    @Override
    public SchedulePreferences getSchedulePreferencesUser(int userId) {
        return successOrNull(()-> {
            Map<String, Object> sqlMap = jdbcTemplate.queryForMap("SELECT * FROM schedule_preferences WHERE user_id=:userId",Map.of("user_id",userId));
            Map<String, Object> parsableMap = snake2Camel(sqlMap);
            return SchedulePreferences.fromJson(parsableMap);
        });
    }

    @Override
    public User getUser(int userId) {
        return successOrNull(()-> {
            Map<String, Object> sqlMap = jdbcTemplate.queryForMap("SELECT * FROM user_table WHERE id=:id", Map.of("id",userId));
            Map<String, Object> parsableMap = snake2Camel(sqlMap);
            return User.fromJson(parsableMap);
        });
    }

    @Override
    public Store getUserStore(int userId) {
        return successOrNull(()-> {
            Map<String, Object> sqlMap = jdbcTemplate.queryForMap("SELECT * FROM store WHERE id=(SELECT store_id FROM user_table WHERE id=:userId)", Map.of("user_id", userId));
            Map<String, Object> parsableMap = snake2Camel(sqlMap);
            return Store.fromJson(parsableMap);
        });
    }

    @Override
    public void addWorker(WorkerCreationValues values) {
        jdbcTemplate.queryForMap(
                "WITH " +
                        "request_values(temp_store_id, temp_user_type) AS ((SELECT store_id,type FROM worker_creation_request WHERE key = :key))," +
                        "temp_user_id(id) AS (INSERT INTO user_table (name, email, password, store_id, type) VALUES(:name, :email, :password, (SELECT temp_store_id FROM request_values), (SELECT temp_user_type FROM request_values)) RETURNING id)" +
                        "INSERT INTO schedule_preferences (user_id) VALUES((SELECT id FROM temp_user_id));",map2SqlMap(values.toJson()));
    }

    @Override
    public void addDepartment(DepartmentCreationValues values) {
        jdbcTemplate.update("INSERT INTO user_table (name, email, password, store_id, type) VALUES(:name, :email, :password, (INSERT INTO store (address, city) VALUES(:address, :city) RETURNING id), 'administrator')",map2SqlMap(values.toJson()));
    }

    Map<String, Object> snake2Camel(Map<String, Object> snake){
        Map<String, Object> camel = new HashMap<>();
        snake.forEach((snakeKey, object) -> {
            while (snakeKey.contains("_")){
                snakeKey = snakeKey.replaceFirst("_[a-z]",String.valueOf(Character.toUpperCase(snakeKey.charAt(snakeKey.indexOf("_") + 1))));
            }
            String camelKey = snakeKey;
            camel.put(camelKey,object);
        });
        return camel;
    }

    MapSqlParameterSource map2SqlMap(Map<String, Object> map){
        MapSqlParameterSource sqlMap = new MapSqlParameterSource();
        map.forEach((key, object) -> {
            if(!List.of(Integer.class, Double.class, String.class, Long.class).contains(object.getClass())){
                sqlMap.addValue(key, object, Types.OTHER);
            }else {
                sqlMap.addValue(key, object);
            }
        });
        return sqlMap;
    }

    public <T> T successOrNull(ITry<T> run){
        try {
            return run.iTry();
        }catch (Exception e){
            System.err.println(e.toString());
            return null;
        }
    }
}

interface ITry<T>{
    T iTry();
}

