package com.frombooktobook.frombooktobookbackend.domain.post;

import com.frombooktobook.frombooktobookbackend.domain.BaseTimeEntity;
import com.frombooktobook.frombooktobookbackend.domain.liked.Liked;
import com.frombooktobook.frombooktobookbackend.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @Column(name="POST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User writer;

    @Column(nullable = false)
    private String bookName;

    private String title;

    private String bookAuthor;

    private int rate;

    private String content;

    private int likedCount;

    private int views;

    @Builder
    public Post(User writer, String bookName, String bookAuthor, int rate, String content, String title) {
        this.writer = writer;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.rate=rate;
        this.content = content;
        this.title = title;
        this.likedCount=0;
        this.views=0;
    }

    public void update(String bookName, String bookAuthor, String title, String content, int rate) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.title = title;
        this.content = content;
        this.rate=rate;
    }

    public int addLikedCount() {
        likedCount +=1;
        return likedCount;
    }

    public int subtrackLikedCount() {
        likedCount -=1;
        return likedCount;
    }

    public int updateView(int count) {
        views += count;
        return views;
    }
}

