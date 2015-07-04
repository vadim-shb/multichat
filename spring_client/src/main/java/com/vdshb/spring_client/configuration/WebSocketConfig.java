package com.vdshb.spring_client.configuration;

import com.vdshb.spring_client.controllers.SockJsHandler;
import com.vdshb.spring_client.controllers.WsHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    WsHandler wsHandler;
    @Autowired
    SockJsHandler sockJsHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(wsHandler, "/wsHandler").setAllowedOrigins("*");
        registry.addHandler(sockJsHandler, "/sockJsHandler").setAllowedOrigins("*").withSockJS();
    }

}
