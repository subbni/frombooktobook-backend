package com.frombooktobook.frombooktobookbackend.controller.post;

import com.frombooktobook.frombooktobookbackend.domain.post.Post;
import com.frombooktobook.frombooktobookbackend.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class PostCreateRequestDto {
    private String userEmail;
    private String bookName;
    private String bookAuthor;
    private String title;
    private String content;
    private int rate;

    @Builder
    public PostCreateRequestDto(String userEmail, String bookName, String bookAuthor, String content, int rate, String title) {
        this.userEmail= userEmail;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.rate=rate;
        this.content = content;

        if(title!=null) {
            this.title = title;
        } else {
            this.title = bookName+"을 읽고";
        }
    }

    public Post toEntity(User user) {
        return Post.builder()
                .bookName(bookName)
                .bookAuthor(bookAuthor)
                .title(title)
                .content(content)
                .rate(rate)
                .writer(user)
                .build();
    }

}
