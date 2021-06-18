package com.coopschedulingapplication.restapiserver.persistence.Postgres;

import com.coopschedulingapplication.restapiserver.Data.ValueEntities.ChainCreationValues;
import com.coopschedulingapplication.restapiserver.Data.ValueEntities.DepartmentCreationValues;
import com.coopschedulingapplication.restapiserver.Data.ValueEntities.WorkerCreationValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AddUserFunctions {

    NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addChain(ChainCreationValues values) {
        jdbcTemplate.update("" +
                "INSERT INTO user_roles(user_id, role_id) VALUES (" +
                "(INSERT INTO user_values (name, email, password) VALUES(:name, :email, :password) RETURNING id)," +
                "(INSERT INTO chain (name) VALUES(:chain_name))" +
                ");" , HelperFunctions.map2SqlMap(values.toJson()));
    }

    public void addDepartment(DepartmentCreationValues values) {
        jdbcTemplate.update("INSERT INTO user_table (name, email, password, store_id, type) VALUES(:name, :email, :password, (INSERT INTO store (address, city) VALUES(:address, :city) RETURNING id), 'administrator')", HelperFunctions.map2SqlMap(values.toJson()));
    }

    public void addWorker(WorkerCreationValues values) {
        jdbcTemplate.queryForMap(
                "WITH " +
                        "request_values(temp_store_id, temp_user_type) AS ((SELECT store_id,type FROM worker_creation_request WHERE key = :key))," +
                        "temp_user_id(id) AS (INSERT INTO user_table (name, email, password, store_id, type) VALUES(:name, :email, :password, (SELECT temp_store_id FROM request_values), (SELECT temp_user_type FROM request_values)) RETURNING id)" +
                        "INSERT INTO schedule_preferences (user_id) VALUES((SELECT id FROM temp_user_id));", HelperFunctions.map2SqlMap(values.toJson()));
    }

}
