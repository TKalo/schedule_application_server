package com.coopschedulingapplication.restapiserver.Configurations;

import com.coopschedulingapplication.restapiserver.DataObjects.UserType;
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
                .simpDestMatchers("/user/return/currentUserStore").hasAnyAuthority(UserType.store_administrator.toString(), UserType.worker.toString())
                .simpTypeMatchers(CONNECT, UNSUBSCRIBE, DISCONNECT).permitAll()
                .anyMessage().authenticated();
    }




    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
