package com.gabozago.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gabozago.domain.User;
import com.gabozago.domain.chat.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
    Optional<ChatRoom> findBySenderAndReceiver(User sender, User receiver);

    Page<ChatRoom> findAllBySenderAndReceiver(Pageable pageable, User sender, User receiver);
}
