package com.gabozago.dto;

import lombok.Builder;
import lombok.Getter;

public class UserDto {
    @Getter
    @Builder
    public static class UserChatRoomResponse {
        private Long userId;
        private String name;
        private String profileImageUrl;
    }
}
