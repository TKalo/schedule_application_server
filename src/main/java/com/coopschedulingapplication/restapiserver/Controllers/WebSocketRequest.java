package com.coopschedulingapplication.restapiserver.Controllers;

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
    public Object addWorkerCreationRequest(@Payload Map<String,Object> params, Principal principal){
        Map<String, Object> request = persistence.addWorkerCreationRequest(jdbcTemplate,params,principal);
        if(request == null) return false;
        template.convertAndSend(SpringDestinations.userCreationRequestsSubscription + request.get("store_id"), new Post<>(PostCommand.ADD, List.of(request)));
        return true;
    }

    @MessageMapping("/acceptWorkerCreationRequest")
    public Object acceptWorkerCreationRequest(@Payload Map<String, Object> params){
        Map<String, Object> request = persistence.acceptWorkerCreationRequest(jdbcTemplate,params);
        if(request == null) return false;
        template.convertAndSend(SpringDestinations.userCreationRequestsSubscription + request.get("store_id"), new Post<>(PostCommand.UPDATE, List.of(request)));
        return true;
    }

    @MessageMapping("/deleteWorkerCreationRequest")
    public Object deleteWorkerCreationRequest(@Payload Map<String, Object> params){
        Map<String, Object> request = persistence.deleteWorkerCreationRequest(jdbcTemplate,params);
        if(request == null) return false;
        template.convertAndSend(SpringDestinations.userCreationRequestsSubscription + request.get("store_id"), new Post<>(PostCommand.DELETE, List.of(request)));
        return true;
    }

    @MessageMapping("/addShiftTemplate")
    @SendToUser
    public Object addShiftTemplate(@Payload Map<String,Object> params, Principal principal){
        Map<String, Object> request = persistence.addShiftTemplate(jdbcTemplate,params,principal);
        if(request == null) return Map.of("abd","123");
        template.convertAndSend(SpringDestinations.shiftTemplateSubscription + request.get("store_id"), new Post<>(PostCommand.ADD, List.of(request)));
        return Map.of("abd","123");
    }

    @MessageMapping("/updateShiftTemplate")
    @SendToUser
    public Object updateShiftTemplate(@Payload Map<String, Object> params){
        Map<String, Object> request = persistence.updateShiftTemplate(jdbcTemplate,params);
        if(request == null) return false;
        template.convertAndSend(SpringDestinations.shiftTemplateSubscription + request.get("store_id"), new Post<>(PostCommand.UPDATE, List.of(request)));
        return true;
    }

    @MessageMapping("/deleteShiftTemplate")
    @SendToUser
    public Object deleteShiftTemplate(@Payload Map<String, Object> params){
        Map<String, Object> request = persistence.deleteShiftTemplate(jdbcTemplate,params);
        if(request == null) return false;
        template.convertAndSend(SpringDestinations.shiftTemplateSubscription + request.get("store_id"), new Post<>(PostCommand.DELETE, List.of(request)));
        return true;
    }

    @MessageMapping("/setScheduleTemplate")
    public Object setScheduleTemplate(@Payload Map<String,Object> params) {
        Map<String, Object> request = persistence.setScheduleTemplate(jdbcTemplate,params);
        if(request == null) return false;
        template.convertAndSend(SpringDestinations.scheduleTemplateSubscription + request.get("store_id"), new Post<>(PostCommand.DELETE, List.of(request)));
        return true;
    }
}
