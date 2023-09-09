package com.gabozago.dto.redis;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static lombok.AccessLevel.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PUBLIC)
public class PublishMessageDto implements Serializable {
    private static final long serialVersionUID = 2082503192322391880L;

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
