package com.frombooktobook.frombooktobookbackend.controller.liked.dto;

import com.frombooktobook.frombooktobookbackend.domain.liked.LikedRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class LikedRequestDto {
    private String email;
    private Long postId;

    public LikedRequestDto(String email, Long postId) {
        this.email=email;
        this.postId=postId;
    }
}
