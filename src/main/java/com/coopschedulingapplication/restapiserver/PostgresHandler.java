package com.coopschedulingapplication.restapiserver;

import com.coopschedulingapplication.restapiserver.DataObjects.ScheduleTemplate;
import com.coopschedulingapplication.restapiserver.DataObjects.ShiftTemplate;
import com.coopschedulingapplication.restapiserver.DataObjects.WorkerCreationRequest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.security.Principal;
import java.sql.Types;
import java.util.Map;

public class PostgresHandler implements IPersistence {
    @Override
    public WorkerCreationRequest addWorkerCreationRequest(NamedParameterJdbcTemplate jdbcTemplate, WorkerCreationRequest request, Principal principal) {
        try {
            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue("type", request.getType(), Types.OTHER);
            map.addValue("user_id", Integer.parseInt(principal.getName()), Types.INTEGER);
            Map<String, Object> sqlReturn = jdbcTemplate.queryForMap("INSERT INTO worker_creation_request AS wcr (type, store_id) VALUES(:type, (SELECT store_id FROM user_table WHERE id=:user_id)) RETURNING *", map);
            sqlReturn.put("storeId","store_id");
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
            sqlReturn.put("storeId","store_id");
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
            sqlReturn.put("storeId","store_id");
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
            sqlReturn.put("storeId","store_id");
            sqlReturn.put("weekDay","week_day");
            sqlReturn.put("startTime","start_time");
            sqlReturn.put("endTime","end_time");
            sqlReturn.put("workType","work_type");
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
            sqlReturn.put("storeId","store_id");
            sqlReturn.put("weekDay","week_day");
            sqlReturn.put("startTime","start_time");
            sqlReturn.put("endTime","end_time");
            sqlReturn.put("workType","work_type");
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
            sqlReturn.put("storeId","store_id");
            sqlReturn.put("weekDay","week_day");
            sqlReturn.put("startTime","start_time");
            sqlReturn.put("endTime","end_time");
            sqlReturn.put("workType","work_type");
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
            sqlReturn.put("storeId","store_id");
            sqlReturn.put("preferenceDeadline","preference_deadline");
            sqlReturn.put("creationDeadline","creation_deadline");
            sqlReturn.put("initiationDeadline","initiation_deadline");
            return ScheduleTemplate.fromJson(sqlReturn);
        }catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }



}
