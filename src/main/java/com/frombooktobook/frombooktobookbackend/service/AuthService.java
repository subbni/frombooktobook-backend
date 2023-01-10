package com.frombooktobook.frombooktobookbackend.service;

import com.frombooktobook.frombooktobookbackend.controller.auth.dto.AuthResponseDto;
import com.frombooktobook.frombooktobookbackend.controller.auth.dto.LoginRequestDto;
import com.frombooktobook.frombooktobookbackend.controller.auth.dto.SignUpRequestDto;
import com.frombooktobook.frombooktobookbackend.domain.user.ProviderType;
import com.frombooktobook.frombooktobookbackend.domain.user.Role;
import com.frombooktobook.frombooktobookbackend.domain.user.User;
import com.frombooktobook.frombooktobookbackend.domain.user.UserRepository;
import com.frombooktobook.frombooktobookbackend.exception.BadRequestException;
import com.frombooktobook.frombooktobookbackend.exception.WrongPasswordException;
import com.frombooktobook.frombooktobookbackend.mail.MailService;
import com.frombooktobook.frombooktobookbackend.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final MailService mailService;


    public AuthResponseDto login(LoginRequestDto requestDto) {
        if(!userRepository.existsByEmail(requestDto.getEmail())) {
            throw new BadRequestException("존재하지 않는 이메일 계정입니다.");
        }

        User user = userService.findByEmail(requestDto.getEmail());
        if(!userService.checkIfPasswordIsCorrect(user, requestDto.getPassword())) {
            throw new WrongPasswordException("잘못된 비밀번호 입니다.");
        }

        Authentication authentication = loginProcess(user);
        return new AuthResponseDto(tokenProvider.createToken(authentication),user.getName(),user.getEmail());
    }

    public Authentication loginProcess(User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    public void singUp(SignUpRequestDto requestDto) {
        if(userRepository.existsByEmail(requestDto.getEmail())) {
            throw new BadRequestException("이미 가입된 이메일입니다.");
        }

        userRepository.save(
                User.builder()
                        .email(requestDto.getEmail())
                        .name(requestDto.getName())
                        .password(passwordEncoder.encode(requestDto.getPassword()))
                        .providerType(ProviderType.LOCAL)
                        .role(Role.USER)
                        .build()
        );
    }



}
