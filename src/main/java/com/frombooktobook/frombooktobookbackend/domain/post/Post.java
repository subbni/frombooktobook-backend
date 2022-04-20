package com.frombooktobook.frombooktobookbackend.domain.post;

import com.frombooktobook.frombooktobookbackend.domain.BaseTimeEntity;
import com.frombooktobook.frombooktobookbackend.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User writer;

    @Column(nullable = false)
    private String bookName;

    private String title;

    private String bookAuthor;

    private int rate;

    private String content;

    @Builder
    public Post(User writer, String bookName, String bookAuthor, int rate, String content, String title) {
        this.writer = writer;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.rate=rate;
        this.content = content;
        this.title = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

