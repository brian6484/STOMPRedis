package com.gabozago.domain;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "NAME")
    private String name;

    @Column(name = "REGION")
    private String region;

    @Column(name = "PROFILE_URL")
    private String profileUrl;

    @Column(name = "PROVIDER")
    private String provider;

}
