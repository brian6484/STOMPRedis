package com.gabozago.controller;


// import 생략...

import com.gabozago.domain.chat.ChatMessage;
import com.gabozago.domain.chat.ChatRoom;
import com.gabozago.domain.chat.mapper.ChatMapper;
import com.gabozago.dto.chat.ChatMessageDto;
import com.gabozago.dto.chat.ChatRoomDto;
import com.gabozago.dto.chat.response.MultiResponseDto;
import com.gabozago.dto.chat.response.PageInfo;
import com.gabozago.repository.ChatRoomRepository;
import com.gabozago.service.UserService;
import com.gabozago.service.chat.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chats")
@Slf4j
@Validated
public class ChatRoomController {

    private final RoomService roomService;

    private final UserService userService;

    private final ChatMapper chatMapper;

    @PostMapping
    public ResponseEntity createRoom(@Valid @RequestBody ChatRoomDto.Post postDto, Long senderId){
        Long receiverId = postDto.getUserId();
        Long roomId = roomService.createRoom(receiverId, senderId);
        URI uri = UriComponentsBuilder.newInstance()
                .path("/chats/{room-id}")
                .buildAndExpand(roomId)
                .toUri();
        return ResponseEntity.created(uri).build();
    }

//    open chatroom
    @GetMapping("{room-id}")
    public ResponseEntity openChatRoom(@Positive @PathVariable("room-id") Long roomId){
        ChatRoom chatRoom = roomService.getChatRoomById(roomId);
        ChatRoomDto.ChatRoomResponse chatRoomResponse = chatMapper.chatRoomToRoomResponseDto(chatRoom);
        return new ResponseEntity<>(chatRoomResponse, HttpStatus.OK);
    }

//    list all chatrooms 채팅 목록 조회
    @GetMapping
    public ResponseEntity getChatRooms(@Positive Long senderId,
                                       @Positive @RequestParam(defaultValue = "1") int page,
                                       @Positive @RequestParam(defaultValue = "10") int size){
        Page<ChatRoom> chatRooms = roomService.pageChatRooms(senderId, page, size);
        PageInfo pageInfo = PageInfo.builder()
                .size(size)
                .page(page)
                .totalElements(chatRooms.getNumberOfElements())
                .totalPages(chatRooms.getTotalPages())
                .build();

        List<ChatRoom> content = chatRooms.getContent();
        List<ChatRoomDto.ChatRoomResponse> chatRoomResponses = chatMapper.chatRoomToRoomResponseDto(content);

        return new ResponseEntity<>(new MultiResponseDto<>(chatRoomResponses, pageInfo), HttpStatus.OK);
    }

}
