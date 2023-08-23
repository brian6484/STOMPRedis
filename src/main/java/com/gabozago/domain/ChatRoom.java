package com.gabozago.domain;

import javax.persistence.*;

@Entity
@Table(name = "CHAT_ROOM")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHAT_ROOM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "LAST_MESSAGE")
    private String lastMessage;

    @Column(name = "READ")
    private int read;

    @Column(name = "SIZE")
    private int size;

}