package com.frombooktobook.frombooktobookbackend.controller.post;

import com.frombooktobook.frombooktobookbackend.domain.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {
    private String bookName;
    private String bookAuthor;
    private String title;
    private String content;
    private int rate;

    public PostResponseDto(Post post) {
        this.bookName=post.getBookName();
        this.bookAuthor=post.getBookAuthor();
        this.title=post.getTitle();
        this.content=post.getContent();
        this.rate=post.getRate();
    }
}
