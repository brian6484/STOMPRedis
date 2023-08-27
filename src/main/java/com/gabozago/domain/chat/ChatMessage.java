package com.gabozago.domain.chat;

import com.gabozago.domain.User;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "CHAT_MESSAGE")
@Getter
@NoArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long messageId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "CHAT_ROOM_ID")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "SENDER_ID")
    private User sender;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime sendTime;

    public void setSender(User sender){
        this.sender = sender;
    }

    public void setChatRoom(ChatRoom chatRoom){
        this.chatRoom = chatRoom;
    }

    @Builder
    public ChatMessage(Long messageId, ChatRoom chatRoom, User sender, String content, LocalDateTime sendTime) {
        this.messageId = messageId;
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.content = content;
        this.sendTime = sendTime;
    }
}
