package com.vdaoyun.systemapi.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@EnableWebSocket
@Configuration  
public class WebsocketConfigration implements WebSocketConfigurer {
    
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(wsHandler(), "/endpointChat").setAllowedOrigins("*").withSockJS();
	}

	@Bean
	public WsHandler wsHandler() {
		return new WsHandler();
	}
    

}
