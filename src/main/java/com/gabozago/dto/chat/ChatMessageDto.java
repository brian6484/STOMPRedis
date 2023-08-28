package com.gabozago.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static lombok.AccessLevel.PROTECTED;
import static lombok.AccessLevel.PUBLIC;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PUBLIC)
public class ChatMessageDto implements Serializable {

    private static final long serialVersionUID = 2082503196922391880L;

    @NotNull
    private Long roomId;

    @NotNull
    private Long senderId;

    @NotBlank
    private String content;

    public void setContent(String content){
        this.content = content;
    }
}
