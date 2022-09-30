package com.frombooktobook.frombooktobookbackend.controller.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateRequestDto {
    private String email;
    private Long postId;
    private String content;

    public CommentCreateRequestDto(String email, Long postId, String content) {
        this.email = email;
        this.postId = postId;
        this.content = content;
    }
}
