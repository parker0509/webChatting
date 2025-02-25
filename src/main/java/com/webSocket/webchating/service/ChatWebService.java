package com.webSocket.webchating.service;

import com.webSocket.webchating.dto.ChatMessageDTO;
import com.webSocket.webchating.entity.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.webSocket.webchating.repository.ChatRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class ChatWebService {

    private final ChatRepository chatRepository;

    @Autowired
    public ChatWebService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public List<ChatMessageDTO> getAllMessage() {
        List<ChatMessage> messages = chatRepository.findAll();

        return messages.stream().map(this::convertMessageDto).collect(Collectors.toList());
    }

    public ChatMessageDTO convertMessageDto(ChatMessage chatMessage) {
        return new ChatMessageDTO(chatMessage.getId(), chatMessage.getSender(),
                chatMessage.getMessage(), chatMessage.getTimestamp());

    }


    @Transactional
    public ChatMessageDTO saveChatMessage(String sender, String message, String timestamp) {
        System.out.println("Received message to save: " + sender + " - " + message + " - " + timestamp);
        ChatMessage chatMessage = new ChatMessage(null, sender, message, timestamp);
        System.out.println("Saving chat message: " + chatMessage);
        ChatMessage savedChatMessage = chatRepository.save(chatMessage);
        System.out.println("Saved message: " + savedChatMessage);
        return convertMessageDto(savedChatMessage);
    }


}
