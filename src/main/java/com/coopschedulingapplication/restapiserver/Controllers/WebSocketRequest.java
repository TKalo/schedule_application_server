package com.coopschedulingapplication.restapiserver.Controllers;

import com.coopschedulingapplication.restapiserver.DataObjects.ScheduleTemplate;
import com.coopschedulingapplication.restapiserver.DataObjects.ShiftTemplate;
import com.coopschedulingapplication.restapiserver.DataObjects.WorkerCreationRequest;
import com.coopschedulingapplication.restapiserver.IPersistence;
import com.coopschedulingapplication.restapiserver.PostgressHandler;
import com.coopschedulingapplication.restapiserver.SpringDestinations;
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
@MessageMapping("/request")
public class WebSocketRequest {

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    IPersistence persistence = new PostgressHandler();

    @MessageMapping("/addWorkerCreationRequest")
    @SendToUser
    public boolean addWorkerCreationRequest(@Payload Map<String,Object> params, Principal principal){
        Map<String, Object> request = persistence.addWorkerCreationRequest(jdbcTemplate, WorkerCreationRequest.fromJson(params),principal);
        if(request == null) return false;
        template.convertAndSend(SpringDestinations.userCreationRequestsSubscription + request.get("store_id"), new Post<>(PostCommand.ADD, List.of(request)));
        return true;
    }

    @MessageMapping("/acceptWorkerCreationRequest")
    @SendToUser
    public boolean acceptWorkerCreationRequest(@Payload Map<String, Object> params){
        Map<String, Object> request = persistence.acceptWorkerCreationRequest(jdbcTemplate,WorkerCreationRequest.fromJson(params));
        if(request == null) return false;
        template.convertAndSend(SpringDestinations.userCreationRequestsSubscription + request.get("store_id"), new Post<>(PostCommand.UPDATE, List.of(request)));
        return true;
    }

    @MessageMapping("/deleteWorkerCreationRequest")
    @SendToUser
    public boolean deleteWorkerCreationRequest(@Payload Map<String, Object> params){
        Map<String, Object> request = persistence.deleteWorkerCreationRequest(jdbcTemplate,WorkerCreationRequest.fromJson(params));
        if(request == null) return false;
        template.convertAndSend(SpringDestinations.userCreationRequestsSubscription + request.get("store_id"), new Post<>(PostCommand.DELETE, List.of(request)));
        return true;
    }

    @MessageMapping("/addShiftTemplate")
    @SendToUser
    public boolean addShiftTemplate(@Payload Map<String,Object> params, Principal principal){
        Map<String, Object> request = persistence.addShiftTemplate(jdbcTemplate,ShiftTemplate.fromJson(params),principal);
        if(request == null) return false;
        template.convertAndSend(SpringDestinations.shiftTemplateSubscription + request.get("store_id"), new Post<>(PostCommand.ADD, List.of(request)));
        return true;
    }

    @MessageMapping("/updateShiftTemplate")
    @SendToUser
    public boolean updateShiftTemplate(@Payload Map<String, Object> params){
        Map<String, Object> request = persistence.updateShiftTemplate(jdbcTemplate,ShiftTemplate.fromJson(params));
        if(request == null) return false;
        template.convertAndSend(SpringDestinations.shiftTemplateSubscription + request.get("store_id"), new Post<>(PostCommand.UPDATE, List.of(request)));
        return true;
    }

    @MessageMapping("/deleteShiftTemplate")
    @SendToUser
    public boolean deleteShiftTemplate(@Payload Map<String, Object> params){
        Map<String, Object> request = persistence.deleteShiftTemplate(jdbcTemplate, ShiftTemplate.fromJson(params));
        if(request == null) return false;
        template.convertAndSend(SpringDestinations.shiftTemplateSubscription + request.get("store_id"), new Post<>(PostCommand.DELETE, List.of(request)));
        return true;
    }

    @MessageMapping("/setScheduleTemplate")
    public boolean setScheduleTemplate(@Payload Map<String,Object> params) {
        Map<String, Object> request = persistence.setScheduleTemplate(jdbcTemplate, ScheduleTemplate.fromJson(params));
        if(request == null) return false;
        template.convertAndSend(SpringDestinations.scheduleTemplateSubscription + request.get("store_id"), new Post<>(PostCommand.DELETE, List.of(request)));
        return true;
    }
}
