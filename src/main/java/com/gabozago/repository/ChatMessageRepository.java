package com.gabozago.repository;

import com.gabozago.domain.chat.ChatMessage;
import com.gabozago.domain.chat.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    Page<ChatMessage> findByChatRoom(Pageable pageable, ChatRoom chatRoom);
}
