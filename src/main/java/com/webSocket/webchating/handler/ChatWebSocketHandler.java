package com.webSocket.webchating.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webSocket.webchating.dto.ChatMessageDTO;
import com.webSocket.webchating.entity.ChatMessage;
import com.webSocket.webchating.service.ChatWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private ChatWebService chatWebService;
    private static final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Autowired
    public ChatWebSocketHandler(ChatWebService chatWebService) {
        this.chatWebService = chatWebService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("웹소켓 연결됨: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("수신된 메시지: " + message.getPayload());

        // 클라이언트에서 받은 메시지를 JSON 파싱하여 sender와 message를 분리
        ObjectMapper objectMapper = new ObjectMapper();
        ChatMessageDTO chatMessageDto = objectMapper.readValue(message.getPayload(), ChatMessageDTO.class);

        // 받은 메시지를 모든 연결된 클라이언트에게 전송
        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                // 메시지를 JSON 형식으로 다시 전송
                s.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessageDto)));
            }
        }

        ChatMessageDTO saveChatMessageDto = chatWebService.saveChatMessage(chatMessageDto.getSender(),

                chatMessageDto.getMessage(), chatMessageDto.getTimestamp());
        
    }



    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("연결 종료됨: " + session.getId());
    }
}
