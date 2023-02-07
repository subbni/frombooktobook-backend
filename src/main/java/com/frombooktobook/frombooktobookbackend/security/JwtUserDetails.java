package com.frombooktobook.frombooktobookbackend.security;

import com.frombooktobook.frombooktobookbackend.domain.user.Role;
import com.frombooktobook.frombooktobookbackend.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

public class JwtUserDetails implements UserDetails, OAuth2User {

    private Long id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String,Object>  attributes;


    public JwtUserDetails(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id=id;
        this.email=email;
        this.password=password;
        this.authorities=authorities;
    }

    // UserDetails : Form 로그인 시 사용
    public static JwtUserDetails create(User user) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        return new JwtUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public static JwtUserDetails create(User user, String roleKey) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new JwtUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );

    }


    public static JwtUserDetails create(User user, Map<String,Object> attributes) {
        JwtUserDetails jwtUserDetails = JwtUserDetails.create(user, user.getRoleKey());
        jwtUserDetails.setAttributes(attributes);
        return jwtUserDetails;
    }

    /**
     * UserDetails 구현
     *
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    // 일단 password는 lastName으로 설정
    @Override
    public String getPassword() {
        return password;
    }

    // PK값 반환
    @Override
    public String getUsername() {
        return email;
    }

    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠김 여부
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정 비밀번호 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //  계정 활성화 여부
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * OAuth2User 구현
     *
     */
    @Override
    public Map<String,Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
       return String.valueOf(id);
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}
