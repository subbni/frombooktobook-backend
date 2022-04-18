package com.frombooktobook.frombooktobookbackend.security.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence (HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException)
            throws IOException {
        // 유효한 유저 정보 없이 접근시 401 응답
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        System.out.println("401 error occured.");
    }
}
