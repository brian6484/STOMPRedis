package com.gabozago.service.chat;

import com.gabozago.domain.User;
import com.gabozago.domain.chat.ChatRoom;
import com.gabozago.repository.ChatRoomRepository;
import com.gabozago.service.UserService;
import com.gabozago.service.redis.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RoomService {
    private final ChatRoomRepository chatRoomRepository;

    private final UserService userService;

    public Long createRoom(Long receiverId, Long senderId) {
        User receiver = getUserById(receiverId);
        User sender = getUserById(senderId);

        Optional<ChatRoom> existingRoom1 = findExistingChatRoom(sender, receiver);
        Optional<ChatRoom> existingRoom2 = findExistingChatRoom(receiver, sender);

        if (existingRoom1.isPresent() || existingRoom2.isPresent()) {
            return handleExistingChatRoom(existingRoom1.orElse(existingRoom2.get()));
        } else {
            return createNewChatRoom(sender, receiver);
        }
    }

    private User getUserById(Long userId) {
        return userService.validateUser(userId);
    }

    private Optional<ChatRoom> findExistingChatRoom(User sender, User receiver) {
        return chatRoomRepository.findBySenderOrReceiver(sender, receiver);
    }

    private Long handleExistingChatRoom(ChatRoom existingRoom) {
        Long existingRoomId = existingRoom.getId();
        chatRoomRepository.findById(existingRoomId);
        log.info("Existing room found: {}", existingRoomId);
        return existingRoomId;
    }

    private Long createNewChatRoom(User sender, User receiver) {
        ChatRoom newChatRoom = ChatRoom.builder()
                .sender(sender)
                .receiver(receiver)
                .build();
        ChatRoom savedChatRoom = chatRoomRepository.save(newChatRoom);
        return savedChatRoom.getId();
    }

    //get user's chatting rooms
    public Page<ChatRoom> pageChatRooms(Long senderId, int page , int size){
        User user = userService.validateUser(senderId);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("roomId").descending());
        return chatRoomRepository.findAllBySenderOrReceiver(pageable, user, user);
    }

    //find ONE 1 chatroom
    public ChatRoom getChatRoomById(Long roomId){
        return chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("room not found with " + roomId));
    }
}
