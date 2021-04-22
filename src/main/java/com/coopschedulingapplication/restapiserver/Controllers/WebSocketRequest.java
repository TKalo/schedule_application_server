package com.coopschedulingapplication.restapiserver.Controllers;

import com.coopschedulingapplication.restapiserver.DataObjects.ScheduleTemplate;
import com.coopschedulingapplication.restapiserver.DataObjects.ShiftTemplate;
import com.coopschedulingapplication.restapiserver.DataObjects.WorkerCreationRequest;
import com.coopschedulingapplication.restapiserver.persistence.IPersistence;
import com.coopschedulingapplication.restapiserver.persistence.PostgresHandler;
import com.coopschedulingapplication.restapiserver.SpringDests;
import com.coopschedulingapplication.restapiserver.StompEntities.Post;
import com.coopschedulingapplication.restapiserver.StompEntities.PostCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@MessageMapping(SpringDests.request)
public class WebSocketRequest {

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    IPersistence persistence = new PostgresHandler();

    @MessageMapping("/addWorkerCreationRequest")
    @SendToUser
    public boolean addWorkerCreationRequest(@Payload Map<String,Object> params, Principal principal){
        WorkerCreationRequest request = persistence.addWorkerCreationRequest(jdbcTemplate, WorkerCreationRequest.fromJson(params),principal);
        if(request == null) return false;
        template.convertAndSend(SpringDests.userCreationRequestSub + request.getStoreId(), new Post<>(PostCommand.ADD, List.of(request)));
        return true;
    }

    @MessageMapping("/acceptWorkerCreationRequest")
    @SendToUser
    public boolean acceptWorkerCreationRequest(@Payload Map<String, Object> params){
        WorkerCreationRequest request = persistence.acceptWorkerCreationRequest(jdbcTemplate,WorkerCreationRequest.fromJson(params));
        if(request == null) return false;
        template.convertAndSend(SpringDests.userCreationRequestSub + request.getStoreId(), new Post<>(PostCommand.UPDATE, List.of(request)));
        return true;
    }

    @MessageMapping("/deleteWorkerCreationRequest")
    @SendToUser
    public boolean deleteWorkerCreationRequest(@Payload Map<String, Object> params){
        WorkerCreationRequest request = persistence.deleteWorkerCreationRequest(jdbcTemplate,WorkerCreationRequest.fromJson(params));
        if(request == null) return false;
        template.convertAndSend(SpringDests.userCreationRequestSub + request.getStoreId(), new Post<>(PostCommand.DELETE, List.of(request)));
        return true;
    }

    @MessageMapping("/addShiftTemplate")
    @SendToUser
    public boolean addShiftTemplate(@Payload Map<String,Object> params, Principal principal){
        ShiftTemplate request = persistence.addShiftTemplate(jdbcTemplate,ShiftTemplate.fromJson(params),principal);
        if(request == null) return false;
        template.convertAndSend(SpringDests.scheduleTemplateSub + request.getStoreId(), new Post<>(PostCommand.ADD, List.of(request)));
        return true;
    }

    @MessageMapping("/updateShiftTemplate")
    @SendToUser
    public boolean updateShiftTemplate(@Payload Map<String, Object> params){
        ShiftTemplate request = persistence.updateShiftTemplate(jdbcTemplate,ShiftTemplate.fromJson(params));
        if(request == null) return false;
        template.convertAndSend(SpringDests.scheduleTemplateSub + request.getStoreId(), new Post<>(PostCommand.UPDATE, List.of(request)));
        return true;
    }

    @MessageMapping("/deleteShiftTemplate")
    @SendToUser
    public boolean deleteShiftTemplate(@Payload Map<String, Object> params){
        ShiftTemplate request = persistence.deleteShiftTemplate(jdbcTemplate, ShiftTemplate.fromJson(params));
        if(request == null) return false;
        template.convertAndSend(SpringDests.scheduleTemplateSub + request.getStoreId(), new Post<>(PostCommand.DELETE, List.of(request)));
        return true;
    }

    @MessageMapping("/setScheduleTemplate")
    public boolean setScheduleTemplate(@Payload Map<String,Object> params) {
        ScheduleTemplate schedule = persistence.setScheduleTemplate(jdbcTemplate, ScheduleTemplate.fromJson(params));
        if(schedule == null) return false;
        template.convertAndSend(SpringDests.shiftTemplateSub + schedule.getStoreId(), new Post<>(PostCommand.DELETE, List.of(schedule)));
        return true;
    }
}
