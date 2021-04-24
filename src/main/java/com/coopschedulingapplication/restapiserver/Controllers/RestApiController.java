package com.coopschedulingapplication.restapiserver.Controllers;

import com.coopschedulingapplication.restapiserver.ConnectionObjects.DepartmentCreationValues;
import com.coopschedulingapplication.restapiserver.ConnectionObjects.WorkerCreationValues;
import com.coopschedulingapplication.restapiserver.SpringDests;
import com.coopschedulingapplication.restapiserver.persistence.IPersistence;
import com.coopschedulingapplication.restapiserver.persistence.PostgresHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class RestApiController {


    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    String error;

    @Autowired
    SimpMessagingTemplate template;

    IPersistence persistence = new PostgresHandler();

    @Transactional
    @PostMapping(SpringDests.add + SpringDests.chain)
    public void addChain(@RequestBody Map<String,String> json){

    }

    @Transactional
    @PostMapping(SpringDests.add + SpringDests.department)
    public void addDepartment(@RequestBody Map<String,Object> json){
        persistence.addDepartment(jdbcTemplate, DepartmentCreationValues.fromJson(json));
    }

    @Transactional
    @PostMapping(SpringDests.add + SpringDests.worker)
    public void addWorker(@RequestBody Map<String,Object> json) {
        error = "found no match for provided key";
        persistence.addWorker(jdbcTemplate, WorkerCreationValues.fromJson(json));
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(Exception e) {
        System.out.println(e.getMessage());
        return error != null ? error : "unknown error";
    }
}















