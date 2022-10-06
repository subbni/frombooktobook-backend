package com.frombooktobook.frombooktobookbackend.controller.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class EmailVertifyRequestDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String code;

    @Builder
    public EmailVertifyRequestDto(String email, String code) {
        this.email=email;
        this.code= code;
    }
}
