package com.gabozago.dto.chat;

import com.gabozago.dto.UserDto;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ChatRoomDto {
    @Getter
    public static class Post {
        @NotNull
        private Long userId;
    }

    @Getter
    @Builder
    public static class ChatRoomResponse{
        private Long chatRoomId;
        private UserDto.UserChatRoomResponse sender;
        private UserDto.UserChatRoomResponse receiver;
    }
    @Getter
    @Builder
    public static class ChatMessageResponse{
        private Long messageId;
        private UserDto.UserChatRoomResponse sender;
        private String content;
        private LocalDateTime sendTime;
    }
}
