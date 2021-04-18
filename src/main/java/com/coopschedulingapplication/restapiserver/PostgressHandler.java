package com.coopschedulingapplication.restapiserver;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.security.Principal;
import java.sql.Types;
import java.util.Map;

public class PostgressHandler implements IPersistence {
    @Override
    public Map<String, Object> addWorkerCreationRequest(NamedParameterJdbcTemplate jdbcTemplate, Map<String, Object> params, Principal principal) {
        try {
            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue("type", params.get("type"), Types.OTHER);
            map.addValue("user_id", Integer.parseInt(principal.getName()), Types.INTEGER);
            return jdbcTemplate.queryForMap("INSERT INTO worker_creation_request AS wcr (type, store_id) VALUES(:type, (SELECT store_id FROM user_table WHERE id=:user_id)) RETURNING *", map);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Map<String, Object> acceptWorkerCreationRequest(NamedParameterJdbcTemplate jdbcTemplate, Map<String, Object> params) {
        try{
            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue("id", params.get("id"), Types.INTEGER);
            map.addValue("accepted", "accepted", Types.OTHER);
            return jdbcTemplate.queryForMap("UPDATE worker_creation_request SET status = :accepted WHERE id = :id RETURNING *",map);
        }catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Map<String, Object> deleteWorkerCreationRequest(NamedParameterJdbcTemplate jdbcTemplate, Map<String, Object> params) {
        try{
            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue("id", params.get("id"), Types.INTEGER);
            return jdbcTemplate.queryForMap("WITH t1 AS (DELETE FROM worker_creation_request WHERE id = :id RETURNING *) SELECT * FROM t1",map);
        }catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Map<String, Object> addShiftTemplate(NamedParameterJdbcTemplate jdbcTemplate, Map<String, Object> params, Principal principal) {
        try {
            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue("week_day", params.get("week_day"), Types.OTHER);
            map.addValue("start_time", params.get("start_time"), Types.BIGINT);
            map.addValue("end_time", params.get("end_time"), Types.BIGINT);
            map.addValue("worker_type", params.get("worker_type"), Types.OTHER);
            map.addValue("user_id", Integer.parseInt(principal.getName()), Types.INTEGER);
            return jdbcTemplate.queryForMap("INSERT INTO shift_template (week_day, start_time, end_time, store_id, worker_type) VALUES(:week_day, :start_time, :end_time, (SELECT store_id FROM user_table WHERE id = :user_id), :worker_type) RETURNING *", map);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Map<String, Object> updateShiftTemplate(NamedParameterJdbcTemplate jdbcTemplate, Map<String, Object> params) {
        try {
            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue("id", params.get("id"), Types.INTEGER);
            map.addValue("week_day", params.get("week_day"), Types.OTHER);
            map.addValue("start_time", params.get("start_time"), Types.BIGINT);
            map.addValue("end_time", params.get("end_time"), Types.BIGINT);
            map.addValue("worker_type", params.get("worker_type"), Types.OTHER);
            return jdbcTemplate.queryForMap("UPDATE shift_template SET week_day = :week_day, start_time = :start_time, end_time = :end_time, worker_type = :worker_type WHERE id = :id RETURNING *", map);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Map<String, Object> deleteShiftTemplate(NamedParameterJdbcTemplate jdbcTemplate, Map<String, Object> params) {
        try {
            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue("id", params.get("id"), Types.INTEGER);
            return jdbcTemplate.queryForMap("WITH t1 AS (DELETE FROM shift_template WHERE id = :id RETURNING *) SELECT * FROM t1", map);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Map<String, Object> setScheduleTemplate(NamedParameterJdbcTemplate jdbcTemplate, Map<String, Object> params) {
        try {
            return jdbcTemplate.queryForMap("UPDATE schedule_template SET preference_deadline = :preference_deadline, creation_deadline=:creation_deadline, initiation_deadline=:initiation_deadline, weeks=:weeks WHERE store_id = :store_id RETURNING *",params);
        }catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }



}
