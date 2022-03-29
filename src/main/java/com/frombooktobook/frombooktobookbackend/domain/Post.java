package com.frombooktobook.frombooktobookbackend.domain;

import lombok.Builder;

import javax.persistence.*;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String user;

    @Column(nullable = false)
    private String bookName;

    private String bookAuthor;

    private int rate;

    private String content;

    @Builder
    public Post(String user, String bookName, String bookAuthor, int rate, String content) {

    }
}

