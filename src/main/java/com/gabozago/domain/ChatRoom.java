package com.gabozago.domain;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "CHAT_ROOM")
public class ChatRoom {

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

    @Column(name = "READ")
    private int read;

    @Column(name = "SIZE")
    private int size;

}