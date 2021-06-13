package com.coopschedulingapplication.restapiserver.Controllers;

import com.coopschedulingapplication.restapiserver.Data.Entities.*;
import com.coopschedulingapplication.restapiserver.SpringDests;
import com.coopschedulingapplication.restapiserver.StompEntities.Post;
import com.coopschedulingapplication.restapiserver.StompEntities.PostCommand;
import com.coopschedulingapplication.restapiserver.persistence.Postgres.PostgresHandler;
import org.springframework.beans.factory.annotation.Autowired;
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
    PostgresHandler persistence;

    @MessageMapping(SpringDests.workerCreationRequest + SpringDests.add)
    @SendToUser
    public boolean addWorkerCreationRequest(@Payload Map<String,Object> params, Principal principal){
        WorkerCreationRequest request = persistence.addWorkerCreationRequest(new WorkerCreationRequest(params),Integer.parseInt(principal.getName()));
        if(request == null) return false;

        System.out.println("addWorkerCreationRequest!!!!");

        template.convertAndSend(SpringDests.userCreationRequestSub + "/" + request.getStoreId(), new Post<>(PostCommand.DELETE, List.of(request)));
        return true;
    }

    @MessageMapping(SpringDests.workerCreationRequest + SpringDests.accept)
    @SendToUser
    public boolean acceptWorkerCreationRequest(@Payload Map<String, Object> params){
        WorkerCreationRequest request = persistence.acceptWorkerCreationRequest(new WorkerCreationRequest(params));
        if(request == null) return false;
        template.convertAndSend(SpringDests.userCreationRequestSub + "/" + request.getStoreId(), new Post<>(PostCommand.UPDATE, List.of(request)));
        return true;
    }

    @MessageMapping(SpringDests.workerCreationRequest + SpringDests.delete)
    @SendToUser
    public boolean deleteWorkerCreationRequest(@Payload Map<String, Object> params){
        WorkerCreationRequest request = persistence.deleteWorkerCreationRequest(new WorkerCreationRequest(params));
        if(request == null) return false;
        template.convertAndSend(SpringDests.userCreationRequestSub + "/" + request.getStoreId(), new Post<>(PostCommand.DELETE, List.of(request)));
        return true;
    }

    @MessageMapping(SpringDests.shiftTemplate + SpringDests.add)
    @SendToUser
    public boolean addShiftTemplate(@Payload Map<String,Object> params, Principal principal){
        ShiftTemplate request = persistence.addShiftTemplate(new ShiftTemplate(params),Integer.parseInt(principal.getName()));
        if(request == null) return false;
        template.convertAndSend(SpringDests.scheduleTemplateSub + "/" + request.getStoreId(), new Post<>(PostCommand.ADD, List.of(request)));
        return true;
    }

    @MessageMapping(SpringDests.shiftTemplate + SpringDests.update)
    @SendToUser
    public boolean updateShiftTemplate(@Payload Map<String, Object> params){
        ShiftTemplate request = persistence.updateShiftTemplate(new ShiftTemplate(params));
        if(request == null) return false;
        template.convertAndSend(SpringDests.scheduleTemplateSub + "/" + request.getStoreId(), new Post<>(PostCommand.UPDATE, List.of(request)));
        return true;
    }

    @MessageMapping(SpringDests.shiftTemplate + SpringDests.delete)
    @SendToUser
    public boolean deleteShiftTemplate(@Payload Map<String, Object> params){
        ShiftTemplate request = persistence.deleteShiftTemplate(new ShiftTemplate(params));
        if(request == null) return false;
        template.convertAndSend(SpringDests.scheduleTemplateSub + "/" + request.getStoreId(), new Post<>(PostCommand.DELETE, List.of(request)));
        return true;
    }

    @MessageMapping(SpringDests.scheduleTemplate + SpringDests.update)
    public boolean setScheduleTemplate(@Payload Map<String,Object> params) {
        ScheduleTemplate schedule = persistence.setScheduleTemplate(new ScheduleTemplate(params));
        if(schedule == null) return false;
        template.convertAndSend(SpringDests.scheduleTemplate + "/" + schedule.getStoreId(), new Post<>(PostCommand.DELETE, List.of(schedule)));
        return true;
    }

    @MessageMapping(SpringDests.schedulePreferences + SpringDests.update)
    public boolean setSchedulePreferences(@Payload Map<String,Object> params) {
        SchedulePreferences preferences = persistence.setSchedulePreferences(new SchedulePreferences(params));
        if(preferences == null) return false;
        template.convertAndSend(SpringDests.schedulePreferencesSub + "/" + preferences.getUserId(), new Post<>(PostCommand.DELETE, List.of(preferences)));
        return true;
    }
}
