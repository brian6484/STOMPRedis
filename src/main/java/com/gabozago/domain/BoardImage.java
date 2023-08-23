
package com.gabozago.domain;

import javax.persistence.*;

@Entity
@Table(name = "BOARD_IMAGES")
public class BoardImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_IMAGE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @Column(name = "BOARD_IMAGE_URL")
    private String imageUrl;

}
