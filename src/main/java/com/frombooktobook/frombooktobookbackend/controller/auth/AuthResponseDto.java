package com.frombooktobook.frombooktobookbackend.controller.auth;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponseDto {
    private String accessToken;
    private String tokenType="Bearer";

    public AuthResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
