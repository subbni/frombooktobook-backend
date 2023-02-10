package com.frombooktobook.frombooktobookbackend.controller.auth;

import com.frombooktobook.frombooktobookbackend.controller.auth.dto.*;
import com.frombooktobook.frombooktobookbackend.controller.user.dto.PasswordChangeRequestDto;
import com.frombooktobook.frombooktobookbackend.domain.user.User;
import com.frombooktobook.frombooktobookbackend.exception.BadRequestException;
import com.frombooktobook.frombooktobookbackend.exception.ResourceNotFoundException;
import com.frombooktobook.frombooktobookbackend.exception.WrongPasswordException;
import com.frombooktobook.frombooktobookbackend.service.MailService;
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
            return ResponseEntity.ok(authService.login(loginRequest).setSuccess(true));
        }catch(WrongPasswordException | BadRequestException e) {
            return ResponseEntity.ok(new ApiResponseDto(false,e.getMessage())); // TODO: 수정 필요
        }
    }

    // form 회원가입
    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequestDto requestDto) {
        try{
            authService.signUp(requestDto);
            return ResponseEntity.ok(new ApiResponseDto(true, "회원가입 되었습니다."));
        } catch(Exception e) {
            return ResponseEntity.ok(new ApiResponseDto(false,e.getMessage()));
        }
    }


    @GetMapping("/email-code/{email}")
    public ApiResponseDto sendEmailVerifyCode(@PathVariable String email) {
        if(userService.checkIfExistedEmail(email)) {
            return new ApiResponseDto(false,"이미 가입된 이메일입니다.");
        }

        try{
            authService.sendMailCode(email);
            return new ApiResponseDto(true,"이메일 인증 코드가 메일로 전송되었습니다.");
        } catch(Exception e) {
            return new ApiResponseDto(false,e.getMessage());
        }
    }

    @GetMapping("/email-verify/{code}")
    public ApiResponseDto verifyEmailCode(@PathVariable String code) {
        try {
            authService.verifyMailCode(code);
            return new ApiResponseDto(true,"이메일 인증이 완료되었습니다.");
        } catch(Exception e) {
            return new ApiResponseDto(false,e.getMessage());
        }
    }

    @GetMapping("/email-temporary-password/{email}")
    public ApiResponseDto sendTemporaryPassword(@PathVariable String email) {
        if(!userService.checkIfExistedEmail(email)) {
            return new ApiResponseDto(false,"가입되지 않은 이메일입니다.");
        }

        try{
            authService.sendTemporaryPassword(email);
            return new ApiResponseDto(true,"임시 비밀번호가 메일로 전송되었습니다. 로그인 후 비밀번호를 변경해주세요.");
        }catch (Exception e) {
            return new ApiResponseDto(false,e.getMessage());
        }
    }
}
