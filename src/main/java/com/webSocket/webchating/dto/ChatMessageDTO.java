package com.webSocket.webchating.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatMessageDTO {

    private Long id;
    private String sender;
    private String message;
    private String timestamp;



    public ChatMessageDTO(Long id,String sender, String message, String timestamp) {
        this.id=id;
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
    }

    public ChatMessageDTO() {
    }
}
