package com.frombooktobook.frombooktobookbackend.security.jwt;

import com.frombooktobook.frombooktobookbackend.config.AppProperties;
import com.frombooktobook.frombooktobookbackend.security.JwtUserDetails;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class TokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private static long TOKEN_EXPIRE_MSEC = 864000000;
    private static String TOKEN_SECRET_KEY = "lskdjfiawjfojals286k2345flkasdncvjknawoe3234ifjsfjalwejf";
    private AppProperties appProperties;

    public TokenProvider (AppProperties appProperties) {
        this.appProperties = appProperties;
    }
    public String createToken(Authentication authentication) {
        JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
        Date now = new Date();

        Date expireDate = new Date(now.getTime() + TOKEN_EXPIRE_MSEC);

        return Jwts.builder()
                .setSubject(jwtUserDetails.getId().toString())
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, TOKEN_SECRET_KEY)
                .compact();
    }


    public Long getUserIdFromToken(String token) {
        Claims claims = parseClaims(token);

        return Long.parseLong(claims.getSubject());
    }


    // 토큰 정보 검증
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }

    private Claims parseClaims(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}
