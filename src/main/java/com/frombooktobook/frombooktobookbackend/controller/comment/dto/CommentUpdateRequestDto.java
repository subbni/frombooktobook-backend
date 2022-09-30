package com.frombooktobook.frombooktobookbackend.controller.comment.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateRequestDto {
    private Long commentId;
    private String content;

    public CommentUpdateRequestDto(Long commentId, String content) {
        this.commentId = commentId;
        this.content = content;
    }

}
