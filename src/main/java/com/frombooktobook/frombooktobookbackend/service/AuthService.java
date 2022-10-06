package com.frombooktobook.frombooktobookbackend.service;

import com.frombooktobook.frombooktobookbackend.domain.user.UserRepository;
import com.frombooktobook.frombooktobookbackend.mail.MailService;
import com.frombooktobook.frombooktobookbackend.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
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


}
