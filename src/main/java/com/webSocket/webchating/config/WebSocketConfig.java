package com.webSocket.webchating.config;


import com.webSocket.webchating.handler.ChatWebSocketHandler;
import com.webSocket.webchating.service.ChatWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private ChatWebService chatWebService;

    @Autowired
    public WebSocketConfig(ChatWebService chatWebService) {
        this.chatWebService = chatWebService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler(), "/chat")
                .setAllowedOrigins("*");  // 모든 도메인에서의 요청을 허용
    }

    @Bean
    public WebSocketHandler chatHandler(){
        return new ChatWebSocketHandler(chatWebService);
    }
}
