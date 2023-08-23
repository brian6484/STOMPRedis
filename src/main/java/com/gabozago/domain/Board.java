package com.gabozago.domain;

import javax.persistence.*;

@Entity
@Table(name = "BOARDS")
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "REGION")
    private String region;

    @Column(name = "BOARD_TYPE")
    private String type;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

}