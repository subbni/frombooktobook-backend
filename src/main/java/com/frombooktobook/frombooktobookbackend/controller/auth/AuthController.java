package com.frombooktobook.frombooktobookbackend.controller.auth;

import com.frombooktobook.frombooktobookbackend.domain.user.ProviderType;
import com.frombooktobook.frombooktobookbackend.domain.user.User;
import com.frombooktobook.frombooktobookbackend.domain.user.UserRepository;
import com.frombooktobook.frombooktobookbackend.exception.BadRequestException;
import com.frombooktobook.frombooktobookbackend.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> authenticateUser(@RequestBody LoginRequestDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponseDto(token));
    }

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
                .build());

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponseDto(true, "User registered successfully ! "));
    }
}
