package com.frombooktobook.frombooktobookbackend.controller.auth;

import com.frombooktobook.frombooktobookbackend.controller.auth.dto.ApiResponseDto;
import com.frombooktobook.frombooktobookbackend.controller.auth.dto.AuthResponseDto;
import com.frombooktobook.frombooktobookbackend.controller.auth.dto.LoginRequestDto;
import com.frombooktobook.frombooktobookbackend.controller.auth.dto.RegisterRequestDto;
import com.frombooktobook.frombooktobookbackend.domain.user.ProviderType;
import com.frombooktobook.frombooktobookbackend.domain.user.Role;
import com.frombooktobook.frombooktobookbackend.domain.user.User;
import com.frombooktobook.frombooktobookbackend.domain.user.UserRepository;
import com.frombooktobook.frombooktobookbackend.exception.BadRequestException;
import com.frombooktobook.frombooktobookbackend.exception.ResourceNotFoundException;
import com.frombooktobook.frombooktobookbackend.exception.WrongPasswordException;
import com.frombooktobook.frombooktobookbackend.mail.MailService;
import com.frombooktobook.frombooktobookbackend.security.jwt.TokenProvider;
import com.frombooktobook.frombooktobookbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final MailService mailService;

    // form 로그인
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> authenticateUser(@RequestBody LoginRequestDto loginRequest) {

        User user;
        if(!userRepository.existsByEmail(loginRequest.getEmail())){
            throw new BadRequestException("존재하지 않는 이메일 계정입니다.");
        } else {
            user = userService.findByEmail(loginRequest.getEmail());
            if(!userService.checkIfPasswordIsCorrect(user,loginRequest.getPassword())){
                throw new WrongPasswordException("잘못된 password입니다.");
            }
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponseDto(token,user.getName(),user.getEmail()));
    }

    // form 회원가입
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDto requestDto) {
        if(userRepository.existsByEmail(requestDto.getEmail())) {
            throw new BadRequestException("이미 가입된 이메일입니다.");
        }

        User user = userRepository.save(
                User.builder()
                .email(requestDto.getEmail())
                .name(requestDto.getName())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .providerType(ProviderType.LOCAL)
                .role(Role.USER)
                .build());


        return ResponseEntity.ok(new ApiResponseDto(true, "User registered successfully ! "));

    }

    @GetMapping("/tempPassword/{email}")
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
}
