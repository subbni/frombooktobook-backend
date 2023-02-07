package com.frombooktobook.frombooktobookbackend.controller.post.dto;

import com.frombooktobook.frombooktobookbackend.domain.post.Book;
import com.frombooktobook.frombooktobookbackend.domain.post.Post;
import com.frombooktobook.frombooktobookbackend.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCreateRequestDto {
    private String userEmail;
    private String title;
    private String content;
    private Book book;

    @Builder
    public PostCreateRequestDto(String userEmail, String title, String content, Book book) {
        this.userEmail= userEmail;
        this.title = title;
        this.content = content;
        this.book = book;
    }

    public Post toEntity(User user) {
        return Post.builder()
                .writer(user)
                .title(title)
                .content(content)
                .book(book)
                .build();
    }

}
