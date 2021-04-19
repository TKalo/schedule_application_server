package com.coopschedulingapplication.restapiserver;

import com.coopschedulingapplication.restapiserver.DataObjects.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.security.Principal;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PostgresHandler implements IPersistence {
    @Override
    public WorkerCreationRequest addWorkerCreationRequest(NamedParameterJdbcTemplate jdbcTemplate, WorkerCreationRequest request, Principal principal) {
        try {
            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue("type", request.getType(), Types.OTHER);
            map.addValue("user_id", Integer.parseInt(principal.getName()), Types.INTEGER);
            Map<String, Object> sqlReturn = jdbcTemplate.queryForMap("INSERT INTO worker_creation_request AS wcr (type, store_id) VALUES(:type, (SELECT store_id FROM user_table WHERE id=:user_id)) RETURNING *", map);
            sqlReturn.put("storeId",sqlReturn.get("store_id"));
            return WorkerCreationRequest.fromJson(sqlReturn);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public WorkerCreationRequest acceptWorkerCreationRequest(NamedParameterJdbcTemplate jdbcTemplate, WorkerCreationRequest request) {
        try{
            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue("id", request.getId(), Types.INTEGER);
            map.addValue("accepted", "accepted", Types.OTHER);
            Map<String, Object> sqlReturn = jdbcTemplate.queryForMap("UPDATE worker_creation_request SET status = :accepted WHERE id = :id RETURNING *",map);
            sqlReturn.put("storeId",sqlReturn.get("store_id"));
            return WorkerCreationRequest.fromJson(sqlReturn);
        }catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public WorkerCreationRequest deleteWorkerCreationRequest(NamedParameterJdbcTemplate jdbcTemplate, WorkerCreationRequest request) {
        try{
            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue("id", request.getId(), Types.INTEGER);
            Map<String, Object> sqlReturn = jdbcTemplate.queryForMap("WITH t1 AS (DELETE FROM worker_creation_request WHERE id = :id RETURNING *) SELECT * FROM t1",map);
            sqlReturn.put("storeId",sqlReturn.get("store_id"));
            return WorkerCreationRequest.fromJson(sqlReturn);
        }catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public ShiftTemplate addShiftTemplate(NamedParameterJdbcTemplate jdbcTemplate, ShiftTemplate template, Principal principal) {
        try {
            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue("week_day", template.getWeekDay(), Types.OTHER);
            map.addValue("start_time", template.getStartTime(), Types.BIGINT);
            map.addValue("end_time", template.getEndTime(), Types.BIGINT);
            map.addValue("worker_type", template.getWorkerType(), Types.OTHER);
            map.addValue("user_id", Integer.parseInt(principal.getName()), Types.INTEGER);
            Map<String, Object> sqlReturn = jdbcTemplate.queryForMap("INSERT INTO shift_template (week_day, start_time, end_time, store_id, worker_type) VALUES(:week_day, :start_time, :end_time, (SELECT store_id FROM user_table WHERE id = :user_id), :worker_type) RETURNING *", map);
            sqlReturn.put("storeId",sqlReturn.get("store_id"));
            sqlReturn.put("weekDay",sqlReturn.get("week_day"));
            sqlReturn.put("startTime",sqlReturn.get("start_time"));
            sqlReturn.put("endTime",sqlReturn.get("end_time"));
            sqlReturn.put("workerType",sqlReturn.get("worker_type"));
            return ShiftTemplate.fromJson(sqlReturn);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public ShiftTemplate updateShiftTemplate(NamedParameterJdbcTemplate jdbcTemplate, ShiftTemplate template) {
        try {
            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue("id", template.getId(), Types.INTEGER);
            map.addValue("week_day", template.getWeekDay(), Types.OTHER);
            map.addValue("start_time", template.getStartTime(), Types.BIGINT);
            map.addValue("end_time", template.getEndTime(), Types.BIGINT);
            map.addValue("worker_type", template.getWorkerType(), Types.OTHER);
            Map<String, Object> sqlReturn = jdbcTemplate.queryForMap("UPDATE shift_template SET week_day = :week_day, start_time = :start_time, end_time = :end_time, worker_type = :worker_type WHERE id = :id RETURNING *", map);
            sqlReturn.put("storeId",sqlReturn.get("store_id"));
            sqlReturn.put("weekDay",sqlReturn.get("week_day"));
            sqlReturn.put("startTime",sqlReturn.get("start_time"));
            sqlReturn.put("endTime",sqlReturn.get("end_time"));
            sqlReturn.put("workerType",sqlReturn.get("worker_type"));
            return ShiftTemplate.fromJson(sqlReturn);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public ShiftTemplate deleteShiftTemplate(NamedParameterJdbcTemplate jdbcTemplate, ShiftTemplate template) {
        try {
            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue("id", template.getId(), Types.INTEGER);
            Map<String, Object> sqlReturn = jdbcTemplate.queryForMap("WITH t1 AS (DELETE FROM shift_template WHERE id = :id RETURNING *) SELECT * FROM t1", map);
            sqlReturn.put("storeId",sqlReturn.get("store_id"));
            sqlReturn.put("weekDay",sqlReturn.get("week_day"));
            sqlReturn.put("startTime",sqlReturn.get("start_time"));
            sqlReturn.put("endTime",sqlReturn.get("end_time"));
            sqlReturn.put("workerType",sqlReturn.get("worker_type"));
            return ShiftTemplate.fromJson(sqlReturn);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public ScheduleTemplate setScheduleTemplate(NamedParameterJdbcTemplate jdbcTemplate, ScheduleTemplate template) {
        try {
            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue("store_id", template.getStoreId(), Types.INTEGER);
            map.addValue("preference_deadline", template.getPreferenceDeadline(), Types.OTHER);
            map.addValue("creation_deadline", template.getCreationDeadline(), Types.BIGINT);
            map.addValue("initiation_deadline", template.getInitiationDeadline(), Types.BIGINT);
            map.addValue("weeks", template.getWeeks(), Types.INTEGER);
            Map<String, Object> sqlReturn = jdbcTemplate.queryForMap("UPDATE schedule_template SET preference_deadline = :preference_deadline, creation_deadline=:creation_deadline, initiation_deadline=:initiation_deadline, weeks=:weeks WHERE store_id = :store_id RETURNING *",map);
            sqlReturn.put("storeId",sqlReturn.get("store_id"));
            sqlReturn.put("preferenceDeadline",sqlReturn.get("preference_deadline"));
            sqlReturn.put("creationDeadline",sqlReturn.get("creation_deadline"));
            sqlReturn.put("initiationDeadline",sqlReturn.get("initiation_deadline"));
            return ScheduleTemplate.fromJson(sqlReturn);
        }catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public User getUser(NamedParameterJdbcTemplate jdbcTemplate, int userId) {
        Map<String, Object> sqlReturn = jdbcTemplate.queryForMap("SELECT * FROM user_table WHERE id=:id", Map.of("id",userId));
        sqlReturn.put("storeId",sqlReturn.get("store_id"));
        return User.fromJson(sqlReturn);
    }

    @Override
    public Store getUserStore(NamedParameterJdbcTemplate jdbcTemplate, int userId) {
        Map<String, Object> sqlReturn = jdbcTemplate.queryForMap("SELECT * FROM store WHERE id=(SELECT store_id FROM user_table WHERE id=:user_id)", Map.of("user_id", userId));
        return Store.fromJson(sqlReturn);
    }

    @Override
    public List<ShiftTemplate> getStoreShiftTemplates(NamedParameterJdbcTemplate jdbcTemplate, int storeId) {
        List<Map<String, Object>> sqlReturn = jdbcTemplate.queryForList("SELECT * FROM shift_template WHERE store_id=:store_id", Map.of("store_id", storeId));

        return sqlReturn.stream().map(json -> {
            json.put("storeId",json.get("store_id"));
            json.put("weekDay",json.get("week_day"));
            json.put("startTime",json.get("start_time"));
            json.put("endTime",json.get("end_time"));
            json.put("workerType",json.get("worker_type"));
            return ShiftTemplate.fromJson(json);
        }).collect(Collectors.toList());
    }

    @Override
    public List<WorkerCreationRequest> getStoreWorkerCreationRequests(NamedParameterJdbcTemplate jdbcTemplate, int storeId) {
        List<Map<String, Object>> sqlReturn = jdbcTemplate.queryForList("SELECT * FROM worker_creation_request WHERE store_id=:store_id", Map.of("store_id", storeId));
        return sqlReturn.stream().map(json -> {
            json.put("storeId",json.get("store_id"));
            return WorkerCreationRequest.fromJson(json);
        }).collect(Collectors.toList());
    }

    @Override
    public ScheduleTemplate getStoreScheduleTemplate(NamedParameterJdbcTemplate jdbcTemplate, int storeId) {
        Map<String, Object> sqlReturn = jdbcTemplate.queryForMap("SELECT * FROM schedule_template WHERE store_id=:store_id",Map.of("store_id",storeId));
        sqlReturn.put("storeId",sqlReturn.get("store_id"));
        sqlReturn.put("preferenceDeadline",sqlReturn.get("preference_deadline"));
        sqlReturn.put("creationDeadline",sqlReturn.get("creation_deadline"));
        sqlReturn.put("initiationDeadline",sqlReturn.get("initiation_deadline"));
        return ScheduleTemplate.fromJson(sqlReturn);
    }


}
