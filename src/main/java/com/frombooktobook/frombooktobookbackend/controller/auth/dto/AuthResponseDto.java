package com.frombooktobook.frombooktobookbackend.controller.auth.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponseDto {
    private String name;
    private String email;
    private String accessToken;
    private String tokenType="Bearer";

    public AuthResponseDto(String accessToken, String name, String email) {
        this.accessToken = accessToken;
        this.name = name;
        this.email=email;
    }


}
