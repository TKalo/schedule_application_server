package com.coopschedulingapplication.restapiserver.persistence.Postgres;

import com.coopschedulingapplication.restapiserver.Data.Entities.WorkerCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class WorkerCreationFunctions {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public WorkerCreationRequest add(WorkerCreationRequest request, int userId) {
        return HelperFunctions.successOrNull(()->{

            String sql = "INSERT INTO worker_creation_request AS wcr (type, store_id) VALUES(:type, (SELECT store_id FROM user_table WHERE id=:userId)) RETURNING *";

            Map<String, Object> params = request.toJson();

            params.put("userId",userId);

            MapSqlParameterSource sqlParams = HelperFunctions.map2SqlMap(params);

            Map<String, Object> sqlMap = jdbcTemplate.queryForMap(sql, sqlParams);

            Map<String, Object> parsableMap = HelperFunctions.snake2Camel(sqlMap);

            return new WorkerCreationRequest(HelperFunctions.snake2Camel(parsableMap));
        });
    }

    public WorkerCreationRequest accept(WorkerCreationRequest request) {
        return HelperFunctions.successOrNull(()-> {
            Map<String, Object> sqlMap = jdbcTemplate.queryForMap("UPDATE worker_creation_request SET status = :accepted WHERE id = :id RETURNING *", HelperFunctions.map2SqlMap(request.toJson()));
            Map<String, Object> parsableMap = HelperFunctions.snake2Camel(sqlMap);
            return new WorkerCreationRequest(parsableMap);
        });
    }
    public WorkerCreationRequest delete(WorkerCreationRequest request) {
        return HelperFunctions.successOrNull(()-> {
            Map<String, Object> sqlMap = jdbcTemplate.queryForMap("WITH t1 AS (DELETE FROM worker_creation_request WHERE id = :id RETURNING *) SELECT * FROM t1", HelperFunctions.map2SqlMap(request.toJson()));
            Map<String, Object> parsableMap = HelperFunctions.snake2Camel(sqlMap);
            return new WorkerCreationRequest(parsableMap);
        });
    }

    public List<WorkerCreationRequest> getByStore(int storeId) {
        return HelperFunctions.successOrNull(()-> {
            List<Map<String, Object>> sqlMapList = jdbcTemplate.queryForList("SELECT * FROM worker_creation_request WHERE store_id=:storeId", Map.of("storeId", storeId));
            return sqlMapList.stream().map(sqlMap -> {
                Map<String, Object> parsableMap = HelperFunctions.snake2Camel(sqlMap);
                return new WorkerCreationRequest(parsableMap);
            }).collect(Collectors.toList());
        });
    }
}
