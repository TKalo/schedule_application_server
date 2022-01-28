package com.coopschedulingapplication.restapiserver.Database.Postgres;

import com.coopschedulingapplication.restapiserver.DataTypes.Entities.ShiftTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ShiftTemplateFunctions {

    private PostgresGenericFunctions PGF;
    @Autowired
    private void setPostgresGenericFunctions(PostgresGenericFunctions PGF) {
        this.PGF = PGF;
    }


    public ShiftTemplate add(ShiftTemplate template, int userId) {
        String sql = "INSERT INTO shift_template (week_day, start_time, end_time, store_id, worker_type) VALUES(:weekDay, :startTime, :endTime, (SELECT store_id FROM user_table WHERE id = :userId), :workerType) RETURNING *";
        Map<String, Object> params = template.toJson();
        params.put("userId",userId);
        return HelperFunctions.successOrNull(()-> new ShiftTemplate(PGF.queryMap(sql, params)));
    }

    public ShiftTemplate update(ShiftTemplate template) {
        String sql = "UPDATE shift_template SET week_day = :weekDay, start_time = :startTime, end_time = :endTime, worker_type = :workerType WHERE id = :id RETURNING *";
        Map<String, Object> params = template.toJson();
        return HelperFunctions.successOrNull(()-> new ShiftTemplate(PGF.queryMap(sql, params)));
    }

    public ShiftTemplate delete(ShiftTemplate template) {
        String sql = "WITH t1 AS (DELETE FROM shift_template WHERE id = :id RETURNING *) SELECT * FROM t1";
        Map<String, Object> params = template.toJson();
        return HelperFunctions.successOrNull(()-> new ShiftTemplate(PGF.queryMap(sql, params)));
    }

    public List<ShiftTemplate> getByStore(int storeId) {
        String sql = "SELECT * FROM shift_template WHERE store_id=:storeId";
        Map<String, Object> params = Map.of("storeId", storeId);
        return HelperFunctions.successOrNull(()-> PGF.queryList(sql,params).stream().map(ShiftTemplate::new).collect(Collectors.toList()));
    }
}
