package com.coopschedulingapplication.restapiserver.Configurations;

import com.coopschedulingapplication.restapiserver.Data.ValueEntities.PersistenceResult;
import com.coopschedulingapplication.restapiserver.SpringDests;
import com.coopschedulingapplication.restapiserver.persistence.IPersistence;
import com.coopschedulingapplication.restapiserver.persistence.Postgres.PostgresHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.converter.ByteArrayMessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Collections;
import java.util.List;
import java.util.Set;


@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebsocketConnectionConfiguration implements WebSocketMessageBrokerConfigurer {


    IPersistence persistence;
    @Autowired
    private void getIPersistence(PostgresHandler handler){
        persistence = handler;
    }

    @Autowired
    @Qualifier("clientOutboundChannel")
    private MessageChannel clientOutboundChannel;

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        messageConverters.add(new ByteArrayMessageConverter());
        return false;
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                System.out.println("INBOUND: " + message.getHeaders());

                final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if(accessor == null || accessor.getCommand() != StompCommand.CONNECT) return message;

                final String username = accessor.getFirstNativeHeader("email");
                final String password = accessor.getFirstNativeHeader("password");
                final String userType = accessor.getFirstNativeHeader("usertype");


                PersistenceResult<Integer> authentication = persistence.authenticateUser(username, password, userType);

                if(authentication.getError() != null) {
                    System.out.println("authentication failed - ");

                    StompHeaderAccessor headerAccessor = StompHeaderAccessor.create(StompCommand.ERROR);
                    headerAccessor.setMessage(authentication.getError());
                    headerAccessor.setSessionId(accessor.getSessionId());

                    clientOutboundChannel.send(MessageBuilder.createMessage(new byte[0], headerAccessor.getMessageHeaders()));

                    return null;
                }
                Set<GrantedAuthority> authorities = Collections.singleton((GrantedAuthority) userType::toString);
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authentication.getResult(), null, authorities);
                SecurityContextHolder.getContext().setAuthentication(token);
                accessor.setUser(token);

                return message;
            }
        });
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        WebSocketMessageBrokerConfigurer.super.configureClientOutboundChannel(registration);
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                System.out.println("OUTBOUND: " + message.getHeaders());
                return ChannelInterceptor.super.preSend(message, channel);
            }
        });
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(SpringDests.app, SpringDests.user);
        config.setApplicationDestinationPrefixes(SpringDests.app, SpringDests.user);
        config.setUserDestinationPrefix(SpringDests.user);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry config) {
        config.addEndpoint(SpringDests.endPoint).setAllowedOrigins("*");
    }
}
