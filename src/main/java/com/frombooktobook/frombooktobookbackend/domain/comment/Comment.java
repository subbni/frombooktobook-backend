package com.frombooktobook.frombooktobookbackend.domain.comment;


import com.frombooktobook.frombooktobookbackend.domain.BaseTimeEntity;
import com.frombooktobook.frombooktobookbackend.domain.post.Post;
import com.frombooktobook.frombooktobookbackend.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @Column(name="COMMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User writer;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    @NotNull
    @NotBlank
    private String content;

    @Builder
    public Comment(User user, Post post, String content) {
        this.writer = user;
        this.post = post;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }
}
