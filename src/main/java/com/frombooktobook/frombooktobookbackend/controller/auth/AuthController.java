package com.frombooktobook.frombooktobookbackend.controller.auth;

import com.frombooktobook.frombooktobookbackend.controller.auth.dto.*;
import com.frombooktobook.frombooktobookbackend.controller.user.dto.PasswordChangeRequestDto;
import com.frombooktobook.frombooktobookbackend.domain.user.User;
import com.frombooktobook.frombooktobookbackend.exception.BadRequestException;
import com.frombooktobook.frombooktobookbackend.exception.ResourceNotFoundException;
import com.frombooktobook.frombooktobookbackend.exception.WrongPasswordException;
import com.frombooktobook.frombooktobookbackend.mail.MailService;
import com.frombooktobook.frombooktobookbackend.service.AuthService;
import com.frombooktobook.frombooktobookbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final MailService mailService;

    // form 로그인
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDto loginRequest) {
        try{
            return ResponseEntity.ok(authService.login(loginRequest));
        }catch(WrongPasswordException | BadRequestException e) {
            return ResponseEntity.ok(new ApiResponseDto(false,e.getMessage())); // TODO: 수정 필요
        }
    }

    // form 회원가입
    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequestDto requestDto) {
        try{
            authService.singUp(requestDto);
            return ResponseEntity.ok(new ApiResponseDto(true, "User registered successfully ! "));
        } catch(Exception e) {
            return ResponseEntity.ok(new ApiResponseDto(false,e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/email-verify/{email}")
    public ApiResponseDto sendEmailVertifyCode(@PathVariable String email) {
        if(userService.checkIfVertified(email)) {
            return new ApiResponseDto(false,"이미 인증된 이메일입니다.");
        }

        try{
            String code = userService.setMailCode(email);
            mailService.sendVertifyEmail(email,code);
            return new ApiResponseDto(true,"이메일 인증 코드가 메일로 전송되었습니다.");
        } catch(Exception e) {
            return new ApiResponseDto(false,e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/email-verify")
    public ApiResponseDto vertifyEmailCode(@RequestBody EmailVertifyRequestDto  requestDto) {
        try {
            userService.vertifyMailCode(requestDto);
            return new ApiResponseDto(true,"이메일 인증이 완료되었습니다.");
        } catch(Exception e) {
            return new ApiResponseDto(false,e.getMessage());
        }
    }

    @GetMapping("/password/temporary")
    public ApiResponseDto issueTemporaryPassword(@PathVariable String email) {
        try{
            User user = userService.findByEmail(email);
            String tempPassword = userService.changePasswordToTempPassword(user);
            mailService.sendTempPasswordEmail(user.getEmail(),tempPassword);
            return new ApiResponseDto(true, "임시 비밀번호가 메일로 전송되었습니다.");
        } catch(ResourceNotFoundException e) {
            return new ApiResponseDto(false, "존재하지 않은 이메일입니다.");
        } catch(Exception e) {
            return new ApiResponseDto(false, "mail exception occured.");
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/password/change")
    public ApiResponseDto changePassword(@RequestBody PasswordChangeRequestDto requestDto) {
        try {
            userService.changePasswordToNewPassword(requestDto);
            return new ApiResponseDto(true, "password successfully changed.");
        } catch (Exception e) {
            return new ApiResponseDto(false, e.getMessage());
        }
    }
}
