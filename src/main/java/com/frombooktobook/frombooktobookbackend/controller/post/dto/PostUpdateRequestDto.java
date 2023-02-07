package com.frombooktobook.frombooktobookbackend.controller.post.dto;

import com.frombooktobook.frombooktobookbackend.domain.post.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateRequestDto {
    private Long postId;
    private String title;
    private String content;
    private String bookTitle;
    private String bookAuthor;
    private int bookRate;

    public PostUpdateRequestDto(Long postId,String title, String content, String bookTitle,String bookAuthor,int bookRate) {
        this.postId=postId;
        this.title=title;
        this.content=content;
        this.bookTitle=bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookRate = bookRate;
    }
}
