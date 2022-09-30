package com.frombooktobook.frombooktobookbackend.controller.post.dto;

import com.frombooktobook.frombooktobookbackend.domain.post.Post;
import com.frombooktobook.frombooktobookbackend.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateRequestDto {
    private Long postId;
    private String bookName;
    private String bookAuthor;
    private String title;
    private String content;
    private int rate;

    public PostUpdateRequestDto(Long postId, String bookName, String bookAuthor, String title, String content, int rate ) {
        this.postId=postId;
        this.bookAuthor=bookAuthor;
        this.bookName=bookName;
        this.title=title;
        this.content=content;
        this.rate=rate;
    }
}
