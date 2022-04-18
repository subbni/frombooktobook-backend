package com.frombooktobook.frombooktobookbackend.security.jwt;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest reauest, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        // 유저 정보는 있으나 필요한 권한이 없을 경우 403 응답
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}
