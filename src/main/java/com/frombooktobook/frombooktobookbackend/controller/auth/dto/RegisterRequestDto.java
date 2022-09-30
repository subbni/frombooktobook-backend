package com.frombooktobook.frombooktobookbackend.controller.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequestDto {
    private String email;
    private String name;
    private String password;

    @Builder
    public RegisterRequestDto(String email, String name, String password) {
        this.email= email;
        this.name=name;
        this.password = password;
    }
}
