package com.coopschedulingapplication.restapiserver.Controllers;

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

    @SubscribeMapping("/scheduleTemplate/{store_id}")
    public Post<Map<String,Object>> getScheduleTemplate(@DestinationVariable String store_id) {
        System.out.println("/subscribe/scheduleTemplate");
        return new Post<>(PostCommand.ADD, jdbcTemplate.queryForList("SELECT * FROM schedule_template WHERE store_id=:store_id",Map.of("store_id",Integer.parseInt(store_id))));
    }

    @SubscribeMapping("/userCreationRequests/{store_id}")
    public Post<Map<String,Object>> getUserCreationRequests(@DestinationVariable String store_id) {
        System.out.println("/subscribe/userCreationRequests");
        return new Post<>(PostCommand.ADD, jdbcTemplate.queryForList("SELECT * FROM worker_creation_request WHERE store_id=:store_id", Map.of("store_id", Integer.parseInt(store_id))));
    }

    @SubscribeMapping("/shiftTemplate/{store_id}")
    public Post<Map<String,Object>> getShiftTemplateRequests(@DestinationVariable String store_id) {
        System.out.println("/subscribe/shiftTemplate");
        return new Post<>(PostCommand.ADD, jdbcTemplate.queryForList("SELECT * FROM shift_template WHERE store_id=:store_id", Map.of("store_id", Integer.parseInt(store_id))));
    }

    @SubscribeMapping("/currentUser")
    public Map<String,Object> getCurrentUser(Principal principal){
        Map<String,Object> user = jdbcTemplate.queryForMap("SELECT * FROM user_table WHERE id=:id", Map.of("id",Integer.valueOf(principal.getName())));
        user.remove("password");
        return user;
    }

    @SubscribeMapping("/currentUserStore")
    public Map<String,Object> getCurrentUserStore(Principal principal) {
        return jdbcTemplate.queryForMap("SELECT * FROM store WHERE id=(SELECT store_id FROM user_table WHERE id=:user_id)", Map.of("user_id", Integer.parseInt(principal.getName())));
    }
}
