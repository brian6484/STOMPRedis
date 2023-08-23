package com.gabozago.domain;

import javax.persistence.*;

@Entity
@Table(name = "COMMENTS")
public class Comment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "PARENT_ID")
    private Long parentId;

}
