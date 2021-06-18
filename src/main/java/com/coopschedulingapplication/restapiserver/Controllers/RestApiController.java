package com.coopschedulingapplication.restapiserver.Controllers;

import com.coopschedulingapplication.restapiserver.Data.ValueEntities.DepartmentCreationValues;
import com.coopschedulingapplication.restapiserver.Data.ValueEntities.WorkerCreationValues;
import com.coopschedulingapplication.restapiserver.SpringDests;
import com.coopschedulingapplication.restapiserver.persistence.IPersistence;
import com.coopschedulingapplication.restapiserver.persistence.Postgres.PostgresHandler;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class RestApiController {

    String error;

    IPersistence persistence = new PostgresHandler();

    @Transactional
    @PostMapping(SpringDests.add + SpringDests.chain)
    public void addChain(@RequestBody Map<String,String> json){

    }

    @Transactional
    @PostMapping(SpringDests.add + SpringDests.department)
    public void addDepartment(@RequestBody Map<String,Object> json){
        persistence.addDepartment(new DepartmentCreationValues(json));
    }

    @Transactional
    @PostMapping(SpringDests.add + SpringDests.worker)
    public void addWorker(@RequestBody Map<String,Object> json) {
        error = "found no match for provided key";
        persistence.addWorker(WorkerCreationValues.fromJson(json));
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(Exception e) {
        System.out.println(e.getMessage());
        return error != null ? error : "unknown error";
    }
}
















