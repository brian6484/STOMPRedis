package com.gabozago.domain.chat.mapper;

import com.gabozago.domain.chat.ChatMessage;
import com.gabozago.domain.chat.ChatRoom;
import com.gabozago.dto.chat.ChatMessageDto;
import com.gabozago.dto.chat.ChatRoomDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    ChatRoomDto.ChatMessageResponse messageToMessageResponeDto(ChatMessage chatMessage);

    List<ChatRoomDto.ChatMessageResponse> messageToMessageResponeDto(List<ChatMessage> chatMessageList);

    ChatRoomDto.ChatRoomResponse chatRoomToRoomResponseDto(ChatRoom chatRoom);

    List<ChatRoomDto.ChatRoomResponse> chatRoomToRoomResponseDto(List<ChatRoom> chatRoomList);

}
