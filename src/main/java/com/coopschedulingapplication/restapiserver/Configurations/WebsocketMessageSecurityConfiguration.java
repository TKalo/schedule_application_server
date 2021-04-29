package com.coopschedulingapplication.restapiserver.Configurations;

import com.coopschedulingapplication.restapiserver.Data.Enums.UserType;
import com.coopschedulingapplication.restapiserver.SpringDests;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

import static org.springframework.messaging.simp.SimpMessageType.*;

@Configuration
public class WebsocketMessageSecurityConfiguration extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(final MessageSecurityMetadataSourceRegistry messages) {
        // You can customize your authorization mapping here.
        messages.nullDestMatcher().permitAll()
                .simpDestMatchers(SpringDests.user + SpringDests.subscribe + SpringDests.currentUser).authenticated()
                .simpDestMatchers(SpringDests.user + SpringDests.subscribe + SpringDests.currentStore).hasAnyAuthority(UserType.store_administrator.toString(), UserType.worker.toString())
                .simpDestMatchers(SpringDests.user + SpringDests.request + SpringDests.workerCreationRequest).hasAnyAuthority(UserType.store_administrator.toString(), UserType.chain_administrator.toString())
                .simpDestMatchers(SpringDests.user + SpringDests.request + SpringDests.shiftTemplate).hasAuthority(UserType.store_administrator.toString())
                .simpDestMatchers(SpringDests.user + SpringDests.request + SpringDests.scheduleTemplate).hasAuthority(UserType.store_administrator.toString())
                .simpTypeMatchers(CONNECT, UNSUBSCRIBE, DISCONNECT).permitAll()
                .anyMessage().authenticated();
    }




    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
