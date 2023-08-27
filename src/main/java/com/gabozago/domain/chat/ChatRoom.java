package com.gabozago.domain.chat;

import com.gabozago.domain.BaseEntity;
import com.gabozago.domain.User;
import com.gabozago.service.chat.MessageService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Auditable;
import org.springframework.web.socket.WebSocketSession;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "CHAT_ROOM")
@NoArgsConstructor
@Getter
public class ChatRoom extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHAT_ROOM_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "SENDER_ID")
    private User sender;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "RECEIVER_ID")
    private User receiver;

    @Column(name = "LAST_MESSAGE")
    private String lastMessage;

    public void setSender(User sender){
        this.sender = sender;
    }

    public void setReceiver(User receiver){
        this.receiver = receiver;
    }

    @Builder
    public ChatRoom(Long id, User sender, User receiver, String lastMessage) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.lastMessage = lastMessage;
    }
}