package com.webSocket.webchating.service;

import com.webSocket.webchating.entity.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.webSocket.webchating.repository.ChatRepository;

import java.util.List;

@Service

public class ChatWebService {

    private final ChatRepository chatRepository;

    @Autowired
    public ChatWebService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public List<ChatMessage> getAllMessage(){
        return chatRepository.findAll();
    }

    public ChatMessage saveChatMessage(String sender, String message, String timestamp){
        ChatMessage chatMessage = new ChatMessage(null, sender,message,timestamp);
        return chatRepository.save(chatMessage);
    }


}
