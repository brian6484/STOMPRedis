package com.gabozago.controller;

import com.gabozago.domain.chat.ChatMessage;
import com.gabozago.domain.chat.ChatRoom;
import com.gabozago.domain.chat.PublishMessage;
import com.gabozago.domain.chat.mapper.ChatMapper;
import com.gabozago.dto.chat.ChatMessageDto;
import com.gabozago.dto.chat.ChatRoomDto;
import com.gabozago.dto.chat.response.MultiResponseDto;
import com.gabozago.dto.chat.response.PageInfo;
import com.gabozago.service.chat.MessageService;
import com.gabozago.service.redis.RedisPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chats/message/")
public class MessageController {
    private final MessageService messageService;

    private final RedisPublisher redisPublisher;

    private final ChatMapper chatMapper;

    @Resource(name = "chatRedisTemplate")
    private final RedisTemplate redisTemplate;

    @MessageMapping("/{room-id}")
    public void message(@DestinationVariable("room-id") Long roomId, ChatMessageDto chatMessageDto){
        PublishMessage publishMessage = new PublishMessage(chatMessageDto.getRoomId(), chatMessageDto.getSenderId(), chatMessageDto.getContent(), LocalDateTime.now());
        log.info("publishMessage:{}", publishMessage.getContent());
        redisPublisher.publish(ChannelTopic.of("room" + roomId), String.valueOf(publishMessage));
        messageService.saveMessage(chatMessageDto, roomId);
    }

    @GetMapping("{room-id}")
    public ResponseEntity getMessages(@Positive @PathVariable("room-id") Long roomId,
                                      @Positive @RequestParam(defaultValue = "1") int page,
                                      @Positive @RequestParam(defaultValue = "10") int size){
        Page<ChatMessage> chatMessages = messageService.pageMessages(roomId, page, size);
        PageInfo pageInfo = PageInfo.builder()
                .page(page)
                .size(size)
                .totalElements(chatMessages.getNumberOfElements())
                .totalPages(chatMessages.getTotalPages())
                .build();
        List<ChatMessage> messageList = chatMessages.getContent();
        List<ChatRoomDto.ChatMessageResponse> chatMessageResponses = chatMapper.messageToMessageResponeDto(messageList);
        return new ResponseEntity<>(new MultiResponseDto<>(chatMessageResponses, pageInfo), HttpStatus.OK);
    }

}
