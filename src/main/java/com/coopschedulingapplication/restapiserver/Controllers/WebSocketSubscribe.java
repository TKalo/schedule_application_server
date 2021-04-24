package com.coopschedulingapplication.restapiserver.Controllers;

import com.coopschedulingapplication.restapiserver.DataObjects.*;
import com.coopschedulingapplication.restapiserver.SpringDests;
import com.coopschedulingapplication.restapiserver.persistence.IPersistence;
import com.coopschedulingapplication.restapiserver.persistence.PostgresHandler;
import com.coopschedulingapplication.restapiserver.StompEntities.Post;
import com.coopschedulingapplication.restapiserver.StompEntities.PostCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

@Controller
@MessageMapping(SpringDests.subscribe)
public class WebSocketSubscribe {

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    IPersistence persistence = new PostgresHandler();

    @SubscribeMapping(SpringDests.scheduleTemplate + SpringDests.storeIdValue)
    public Post<ScheduleTemplate> getScheduleTemplate(@DestinationVariable String storeId) {
        return new Post<>(PostCommand.ADD, List.of(persistence.getScheduleTemplateStore(jdbcTemplate,Integer.parseInt(storeId))));
    }

    @SubscribeMapping(SpringDests.workerCreationRequest + SpringDests.storeIdValue)
    public Post<WorkerCreationRequest> getWorkerCreationRequests(@DestinationVariable String storeId) {
        return new Post<>(PostCommand.ADD, persistence.getWorkerCreationRequestsStore(jdbcTemplate,Integer.parseInt(storeId)));
    }

    @SubscribeMapping(SpringDests.shiftTemplate + SpringDests.storeIdValue)
    public Post<ShiftTemplate> getShiftTemplateRequests(@DestinationVariable String storeId) {
        return new Post<>(PostCommand.ADD, persistence.getShiftTemplatesStore(jdbcTemplate,Integer.parseInt(storeId)));
    }

    @SubscribeMapping(SpringDests.shiftTemplate + SpringDests.userIdValue)
    public Post<SchedulePreferences> getSchedulePreferences(@DestinationVariable String userId) {
        return new Post<>(PostCommand.ADD, List.of(persistence.getSchedulePreferencesUser(jdbcTemplate,Integer.parseInt(userId))));
    }

    @SubscribeMapping(SpringDests.currentUser)
    public User getCurrentUser(Principal principal){
        return persistence.getUser(jdbcTemplate,Integer.parseInt(principal.getName()));
    }

    @SubscribeMapping(SpringDests.currentStore)
    public Store getCurrentStore(Principal principal) {
        return persistence.getUserStore(jdbcTemplate,Integer.parseInt(principal.getName()));
    }
}
