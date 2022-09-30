package com.frombooktobook.frombooktobookbackend.controller.post.dto;

import com.frombooktobook.frombooktobookbackend.domain.post.Post;
import com.frombooktobook.frombooktobookbackend.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PostListResponseDto {
    private Long id;
    private String writerName;
    private String bookName;
    private String bookAuthor;
    private String title;
    private LocalDateTime createdDate;
    private int likedCount;
    private int views;

    public PostListResponseDto(Post post) {
        this.id=post.getId();
        this.writerName=post.getWriter().getName();
        this.bookName=post.getBookName();
        this.bookAuthor=post.getBookAuthor();
        this.title=post.getTitle();
        this.createdDate=post.getCreatedTime();
        this.likedCount= post.getLikedCount();
        this.views = post.getViews();
    }



}
