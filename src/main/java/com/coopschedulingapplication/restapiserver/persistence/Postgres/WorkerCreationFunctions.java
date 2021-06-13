package com.coopschedulingapplication.restapiserver.persistence.Postgres;

import com.coopschedulingapplication.restapiserver.Data.Entities.WorkerCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class WorkerCreationFunctions {

    @Autowired
    PostgresGenericFunctions PGF;

    public WorkerCreationRequest add(WorkerCreationRequest request, int userId) {
        String sql = "INSERT INTO worker_creation_request AS wcr (type, store_id) VALUES(:type, (SELECT store_id FROM user_table WHERE id=:userId)) RETURNING *";
        Map<String, Object> params = request.toJson();
        params.put("userId",userId);
        return HelperFunctions.successOrNull(()-> new WorkerCreationRequest(PGF.queryMap(sql, params)));
    }

    public WorkerCreationRequest accept(WorkerCreationRequest request) {
        String sql = "UPDATE worker_creation_request SET status = :accepted WHERE id = :id RETURNING *";
        Map<String, Object> params = request.toJson();
        return HelperFunctions.successOrNull(()-> new WorkerCreationRequest(PGF.queryMap(sql, params)));
    }
    public WorkerCreationRequest delete(WorkerCreationRequest request) {
        String sql = "WITH t1 AS (DELETE FROM worker_creation_request WHERE id = :id RETURNING *) SELECT * FROM t1";
        Map<String, Object> params = request.toJson();
        return HelperFunctions.successOrNull(()-> new WorkerCreationRequest(PGF.queryMap(sql, params)));
    }

    public List<WorkerCreationRequest> getByStore(int storeId) {
        String sql = "SELECT * FROM worker_creation_request WHERE store_id=:storeId";
        Map<String, Object> params = Map.of("storeId", storeId);
        return HelperFunctions.successOrNull(()-> PGF.queryList(sql,params).stream().map(WorkerCreationRequest::new).collect(Collectors.toList()));
    }

}
