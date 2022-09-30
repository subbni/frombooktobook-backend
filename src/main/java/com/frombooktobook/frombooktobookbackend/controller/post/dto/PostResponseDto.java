package com.frombooktobook.frombooktobookbackend.controller.post.dto;

import com.frombooktobook.frombooktobookbackend.domain.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {

    private Long id;
    private String writerName;
    private String bookName;
    private String bookAuthor;
    private String title;
    private String content;
    private int rate;
    private boolean isWriter;
    private int likedCount;
    private int views;

    public PostResponseDto(Post post) {
        this.id=post.getId();
        this.writerName = post.getWriter().getName();
        this.bookName=post.getBookName();
        this.bookAuthor=post.getBookAuthor();
        this.title=post.getTitle();
        this.content=post.getContent();
        this.rate=post.getRate();
        this.isWriter = false;
        this.likedCount = post.getLikedCount();
        this.views = post.getViews();
    }

    public void setIsWriter(boolean isWriter) {
        this.isWriter=isWriter;
    }

}
