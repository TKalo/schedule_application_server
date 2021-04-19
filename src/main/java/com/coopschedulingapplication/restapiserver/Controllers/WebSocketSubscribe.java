package com.coopschedulingapplication.restapiserver.Controllers;

import com.coopschedulingapplication.restapiserver.DataObjects.*;
import com.coopschedulingapplication.restapiserver.IPersistence;
import com.coopschedulingapplication.restapiserver.PostgresHandler;
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
import java.util.Map;

@Controller
@MessageMapping("/subscribe")
public class WebSocketSubscribe {

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    IPersistence persistence = new PostgresHandler();

    @SubscribeMapping("/scheduleTemplate/{storeId}")
    public Post<ScheduleTemplate> getScheduleTemplate(@DestinationVariable String storeId) {
        System.out.println("/subscribe/scheduleTemplate");
        return new Post<>(PostCommand.ADD, List.of(persistence.getStoreScheduleTemplate(jdbcTemplate,Integer.parseInt(storeId))));
    }

    @SubscribeMapping("/userCreationRequests/{storeId}")
    public Post<WorkerCreationRequest> getUserCreationRequests(@DestinationVariable String storeId) {
        System.out.println("/subscribe/userCreationRequests");
        return new Post<>(PostCommand.ADD, persistence.getStoreWorkerCreationRequests(jdbcTemplate,Integer.parseInt(storeId)));
    }

    @SubscribeMapping("/shiftTemplate/{storeId}")
    public Post<ShiftTemplate> getShiftTemplateRequests(@DestinationVariable String storeId) {
        System.out.println("/subscribe/shiftTemplate");
        return new Post<>(PostCommand.ADD, persistence.getStoreShiftTemplates(jdbcTemplate,Integer.parseInt(storeId)));
    }

    @SubscribeMapping("/currentUser")
    public User getCurrentUser(Principal principal){
        return persistence.getUser(jdbcTemplate,Integer.parseInt(principal.getName()));
    }

    @SubscribeMapping("/currentUserStore")
    public Store getCurrentUserStore(Principal principal) {
        return persistence.getUserStore(jdbcTemplate,Integer.parseInt(principal.getName()));
    }
}
