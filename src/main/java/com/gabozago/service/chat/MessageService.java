package com.gabozago.service.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabozago.domain.User;
import com.gabozago.domain.chat.ChatMessage;
import com.gabozago.domain.chat.ChatRoom;
import com.gabozago.dto.chat.ChatMessageDto;
import com.gabozago.repository.ChatMessageRepository;
import com.gabozago.repository.UserRepository;
import com.gabozago.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final ChatMessageRepository chatMessageRepository;

    private final UserService userService;

    private final RoomService roomService;

    public void saveMessage(ChatMessageDto chatMessageDto, Long roomId){
        User user = userService.validateUser(chatMessageDto.getSenderId());
        ChatRoom room = roomService.getChatRoomById(roomId);
        ChatMessage chatMessage = ChatMessage.builder()
                .content(chatMessageDto.getContent())
                .sender(user)
                .chatRoom(room)
                .sendTime(LocalDateTime.now())
                .build();
        chatMessageRepository.save(chatMessage);
        log.info("message saved successfully!");
    }

    public Page<ChatMessage> pageMessages(Long roomId, int page, int size){
        ChatRoom room = roomService.getChatRoomById(roomId);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("messageId").descending());
        return chatMessageRepository.findByChatRoom(pageable, room);
    }
}
