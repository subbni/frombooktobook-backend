package com.frombooktobook.frombooktobookbackend.controller.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Getter
@NoArgsConstructor
public class PostListRequestDto {
    private String userEmail;
    private Pageable pageable;

    public PostListRequestDto(String userEmail, Pageable pageable) {
        this.userEmail=userEmail;
        this.pageable=pageable;
    }
}
