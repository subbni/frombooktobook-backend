package com.frombooktobook.frombooktobookbackend.controller.post;

import com.frombooktobook.frombooktobookbackend.domain.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostListResponseDto {
    private Long id;
    private String bookName;
    private String bookAuthor;
    private String title;
    private LocalDateTime createdDate;

    public PostListResponseDto(Post post) {
        this.id=post.getId();
        this.bookName=post.getBookName();
        this.bookAuthor=post.getBookAuthor();
        this.title=post.getTitle();
        this.createdDate=post.getCreatedTime();
    }


}
