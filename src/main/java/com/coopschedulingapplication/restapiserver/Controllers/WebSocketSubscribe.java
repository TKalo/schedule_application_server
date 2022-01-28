package com.coopschedulingapplication.restapiserver.Controllers;

import com.coopschedulingapplication.restapiserver.DataTypes.Entities.*;
import com.coopschedulingapplication.restapiserver.SpringDests;
import com.coopschedulingapplication.restapiserver.DataTypes.StompEntities.*;
import com.coopschedulingapplication.restapiserver.Database.Postgres.PostgresHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

@Controller
@MessageMapping(SpringDests.subscribe)
public class WebSocketSubscribe {

    @Autowired
    PostgresHandler persistence;

    @SubscribeMapping(SpringDests.scheduleTemplate + SpringDests.storeIdValue)
    public Post<ScheduleTemplate> getScheduleTemplate(@DestinationVariable String storeId) {
        return new Post<>(PostCommand.ADD, List.of(persistence.getScheduleTemplateStore(Integer.parseInt(storeId))));
    }

    @SubscribeMapping(SpringDests.workerCreationRequest + SpringDests.storeIdValue)
    public Post<WorkerCreationRequest> getWorkerCreationRequests(@DestinationVariable String storeId) {
        return new Post<>(PostCommand.ADD, persistence.getWorkerCreationRequestsStore(Integer.parseInt(storeId)));
    }

    @SubscribeMapping(SpringDests.shiftTemplate + SpringDests.storeIdValue)
    public Post<ShiftTemplate> getShiftTemplateRequests(@DestinationVariable String storeId) {
        return new Post<>(PostCommand.ADD, persistence.getShiftTemplatesByStore(Integer.parseInt(storeId)));
    }

    @SubscribeMapping(SpringDests.schedulePreferences + SpringDests.userIdValue)
    public Post<SchedulePreferences> getSchedulePreferences(@DestinationVariable String userId) {
        return new Post<>(PostCommand.ADD, List.of(persistence.getSchedulePreferencesByUser(Integer.parseInt(userId))));
    }

    @SubscribeMapping(SpringDests.currentUser)
    public User getCurrentUser(Principal principal){
        return persistence.getUser(Integer.parseInt(principal.getName()));
    }

    @SubscribeMapping(SpringDests.currentStore)
    public Store getCurrentStore(Principal principal) {
        return persistence.getUserStore(Integer.parseInt(principal.getName()));
    }
}
