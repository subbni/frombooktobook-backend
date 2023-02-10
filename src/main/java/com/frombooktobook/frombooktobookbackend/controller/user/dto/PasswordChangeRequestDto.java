package com.frombooktobook.frombooktobookbackend.controller.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Getter
@Setter
@NoArgsConstructor
public class PasswordChangeRequestDto {
    @Email
    private String email;

    @NotBlank
    private String currentPassword;
    @NotBlank
    private String newPassword;

    @Builder
    public PasswordChangeRequestDto(String email, String currentPassword, String newPassword) {
        this.email=email;
        this.currentPassword=currentPassword;
        this.newPassword=newPassword;
    }
}
