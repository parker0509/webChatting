package com.webSocket.webchating.repository;

import com.webSocket.webchating.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessage,Long> {

}
