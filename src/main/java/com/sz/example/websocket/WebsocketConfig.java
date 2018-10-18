package com.sz.example.websocket;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebsocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
    
	@Resource
	SystemWebSocketHandler systemWebSocketHandler;
	
	@Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(systemWebSocketHandler,"/wsServer").setAllowedOrigins("*").addInterceptors(new WebSocketHandshakeInterceptor());

        registry.addHandler(systemWebSocketHandler, "/wsServer/sockjs").setAllowedOrigins("*").addInterceptors(new WebSocketHandshakeInterceptor()).withSockJS();
//        registry.addHandler(systemWebSocketHandler, "/sockjs/messageserver/info").setAllowedOrigins("*").addInterceptors(new WebSocketHandshakeInterceptor()).withSockJS();
    }

}