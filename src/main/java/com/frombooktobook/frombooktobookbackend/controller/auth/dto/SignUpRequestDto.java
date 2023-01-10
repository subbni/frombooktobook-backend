package com.frombooktobook.frombooktobookbackend.controller.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto {
    private String email;
    private String name;
    private String password;

    @Builder
    public SignUpRequestDto(String email, String name, String password) {
        this.email= email;
        this.name=name;
        this.password = password;
    }
}
