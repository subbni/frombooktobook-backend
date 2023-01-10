package com.frombooktobook.frombooktobookbackend.controller.liked.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LikedResponseDto {
    private String message;
    private boolean isLiked;

    public LikedResponseDto(boolean isLiked, String message) {
        this.message=message;
        this.isLiked = isLiked;
    }
}
