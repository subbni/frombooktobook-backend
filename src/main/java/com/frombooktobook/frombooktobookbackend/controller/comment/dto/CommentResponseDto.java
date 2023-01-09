package com.frombooktobook.frombooktobookbackend.controller.comment.dto;

import com.frombooktobook.frombooktobookbackend.domain.comment.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String email;
    private LocalDateTime localDateTime;
    private String content;
    private boolean isWriter;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.email = comment.getWriter().getEmail();
        this.localDateTime = comment.getCreatedTime();
        this.content = comment.getContent();
        this.isWriter = false;
    }

    public void setIsWriter(boolean isWriter) {
        this.isWriter=isWriter;
    }

}
