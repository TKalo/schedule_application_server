package com.coopschedulingapplication.restapiserver;

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


    @Transactional
    @PostMapping("/addChain")
    public void addChain(@RequestBody Map<String,String> jsonParams){

    }


    @Transactional
    @PostMapping("/addDepartment")
    public void addDepartment(@RequestBody Map<String,String> jsonParams){
        jdbcTemplate.update("INSERT INTO store_employee (name, email, password, store_department_id, type) VALUES(:name, :email, :password, (INSERT INTO store (address, city) VALUES(:address, :city) RETURNING id), 'administrator')",jsonParams);

    }

    @Transactional
    @PostMapping("/addWorker")
    public void addWorker(@RequestBody Map<String,String> jsonParams) {
        error = "found no match for provided key";
        jdbcTemplate.update("INSERT INTO user_table (name, email, password, store_id, type) VALUES(:name, :email, :password, (SELECT store_id FROM worker_creation_request WHERE key = :key), (SELECT type FROM worker_creation_request WHERE key = :key))",jsonParams);
    }

    @CrossOrigin(origins = "*")
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(Exception e) {
        System.out.println(e.getMessage());
        return error != null ? error : "unknown error";
    }
}
















