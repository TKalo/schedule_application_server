package com.coopschedulingapplication.restapiserver;

import com.coopschedulingapplication.restapiserver.DataObjects.ScheduleTemplate;
import com.coopschedulingapplication.restapiserver.DataObjects.ShiftTemplate;
import com.coopschedulingapplication.restapiserver.DataObjects.WorkerCreationRequest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.security.Principal;
import java.sql.Types;
import java.util.Map;

public class PostgressHandler implements IPersistence {
    @Override
    public Map<String, Object> addWorkerCreationRequest(NamedParameterJdbcTemplate jdbcTemplate, WorkerCreationRequest request, Principal principal) {
        try {
            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue("type", request.getType(), Types.OTHER);
            map.addValue("user_id", Integer.parseInt(principal.getName()), Types.INTEGER);
            return jdbcTemplate.queryForMap("INSERT INTO worker_creation_request AS wcr (type, store_id) VALUES(:type, (SELECT store_id FROM user_table WHERE id=:user_id)) RETURNING *", map);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Map<String, Object> acceptWorkerCreationRequest(NamedParameterJdbcTemplate jdbcTemplate, WorkerCreationRequest request) {
        try{
            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue("id", request.getId(), Types.INTEGER);
            map.addValue("accepted", "accepted", Types.OTHER);
            return jdbcTemplate.queryForMap("UPDATE worker_creation_request SET status = :accepted WHERE id = :id RETURNING *",map);
        }catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Map<String, Object> deleteWorkerCreationRequest(NamedParameterJdbcTemplate jdbcTemplate, WorkerCreationRequest request) {
        try{
            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue("id", request.getId(), Types.INTEGER);
            return jdbcTemplate.queryForMap("WITH t1 AS (DELETE FROM worker_creation_request WHERE id = :id RETURNING *) SELECT * FROM t1",map);
        }catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Map<String, Object> addShiftTemplate(NamedParameterJdbcTemplate jdbcTemplate, ShiftTemplate template, Principal principal) {
        try {
            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue("week_day", template.getWeekDay(), Types.OTHER);
            map.addValue("start_time", template.getStartTime(), Types.BIGINT);
            map.addValue("end_time", template.getEndTime(), Types.BIGINT);
            map.addValue("worker_type", template.getWorkerType(), Types.OTHER);
            map.addValue("user_id", Integer.parseInt(principal.getName()), Types.INTEGER);
            return jdbcTemplate.queryForMap("INSERT INTO shift_template (week_day, start_time, end_time, store_id, worker_type) VALUES(:week_day, :start_time, :end_time, (SELECT store_id FROM user_table WHERE id = :user_id), :worker_type) RETURNING *", map);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Map<String, Object> updateShiftTemplate(NamedParameterJdbcTemplate jdbcTemplate, ShiftTemplate template) {
        try {
            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue("id", template.getId(), Types.INTEGER);
            map.addValue("week_day", template.getWeekDay(), Types.OTHER);
            map.addValue("start_time", template.getStartTime(), Types.BIGINT);
            map.addValue("end_time", template.getEndTime(), Types.BIGINT);
            map.addValue("worker_type", template.getWorkerType(), Types.OTHER);
            return jdbcTemplate.queryForMap("UPDATE shift_template SET week_day = :week_day, start_time = :start_time, end_time = :end_time, worker_type = :worker_type WHERE id = :id RETURNING *", map);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Map<String, Object> deleteShiftTemplate(NamedParameterJdbcTemplate jdbcTemplate, ShiftTemplate template) {
        try {
            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue("id", template.getId(), Types.INTEGER);
            return jdbcTemplate.queryForMap("WITH t1 AS (DELETE FROM shift_template WHERE id = :id RETURNING *) SELECT * FROM t1", map);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Map<String, Object> setScheduleTemplate(NamedParameterJdbcTemplate jdbcTemplate, ScheduleTemplate template) {
        try {
            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue("store_id", template.getStoreId(), Types.INTEGER);
            map.addValue("preference_deadline", template.getPreferenceDeadline(), Types.OTHER);
            map.addValue("creation_deadline", template.getCreationDeadline(), Types.BIGINT);
            map.addValue("initiation_deadline", template.getInitiationDeadline(), Types.BIGINT);
            map.addValue("weeks", template.getWeeks(), Types.INTEGER);
            return jdbcTemplate.queryForMap("UPDATE schedule_template SET preference_deadline = :preference_deadline, creation_deadline=:creation_deadline, initiation_deadline=:initiation_deadline, weeks=:weeks WHERE store_id = :store_id RETURNING *",map);
        }catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }



}
