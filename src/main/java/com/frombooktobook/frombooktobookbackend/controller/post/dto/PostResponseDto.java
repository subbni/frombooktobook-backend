package com.frombooktobook.frombooktobookbackend.controller.post.dto;

import com.frombooktobook.frombooktobookbackend.domain.post.Book;
import com.frombooktobook.frombooktobookbackend.domain.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {

    private Long id;
    private String writerName;
    private String title;
    private String content;
    private Book book;
    private LocalDateTime createdDate;
    private boolean isWriter;
    private int likedCount;
    private int views;

    public PostResponseDto(Post post) {
        this.id=post.getId();
        this.writerName = post.getWriter().getName();
        this.title=post.getTitle();
        this.content=post.getContent();
        this.book = post.getBook();
        this.createdDate = post.getCreatedTime();
        this.isWriter = false;
        this.likedCount = post.getLikedCount();
        this.views = post.getHit();
    }

    public PostResponseDto(Post post, boolean isWriter) {
        this.id=post.getId();
        this.writerName = post.getWriter().getName();
        this.title=post.getTitle();
        this.content=post.getContent();
        this.book = post.getBook();
        this.createdDate = post.getCreatedTime();
        this.isWriter = isWriter;
        this.likedCount = post.getLikedCount();
        this.views = post.getHit();
    }

}
