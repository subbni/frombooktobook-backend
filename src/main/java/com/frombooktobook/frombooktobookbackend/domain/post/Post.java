package com.frombooktobook.frombooktobookbackend.domain.post;

import com.frombooktobook.frombooktobookbackend.domain.BaseTimeEntity;
import com.frombooktobook.frombooktobookbackend.domain.Book;
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
    @Column(name="POST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User writer;

    private String title;

    private String content;

    private int likedCount;

    private int hit;

    @OneToOne
    @JoinColumn(name="BOOK_ID")
    private Book book;

    @Builder
    public Post(User writer, String title, String content, Book book) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.book = book;
        this.likedCount=0;
        this.hit=0;
    }

    public void update(String title, String content, Book book) {
        this.title = title;
        this.content = content;
        this.book.update(book.getTitle(),book.getAuthor(),book.getRate());
    }

    public int addLikedCount() {
        likedCount +=1;
        return likedCount;
    }

    public int subtrackLikedCount() {
        likedCount -=1;
        return likedCount;
    }

    public int addHit() {
        this.hit++;
        return hit;
    }
}

