package com.frombooktobook.frombooktobookbackend.security;

import com.frombooktobook.frombooktobookbackend.domain.user.User;
import com.frombooktobook.frombooktobookbackend.domain.user.UserRepository;
import com.frombooktobook.frombooktobookbackend.domain.user.ProviderType;
import com.frombooktobook.frombooktobookbackend.domain.user.Role;
import com.frombooktobook.frombooktobookbackend.exception.OAuthProviderMissMatchException;
import com.frombooktobook.frombooktobookbackend.security.userinfo.OAuth2UserInfo;
import com.frombooktobook.frombooktobookbackend.security.userinfo.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;

/*
Oauth2 공급자로부터 액세스 토큰을 얻은 후 실행될 클래스.
OAuth2 제공 업체에서 받아온 사용자의 세부 정보를 처음으로 fetch.
로그인시 사용자 정보로 서버에 관련해서 해야하는 일들 수행
 */

@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    // 동일한 이메일을 사용하는 사용자가 이미 db 내에 있으면 세부 정보를 업데이트 하고, 그렇지 않으면 새 사용자를 등록한다.

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try{
            return process(userRequest, user);
        } catch (AuthenticationException e) {
            throw new OAuth2AuthenticationException(e.getMessage());
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(),e.getCause());
        }
    }

    // 인증을 요청하는 사용자가 존재하지 않다면 회원가입, 존재한다면 업데이트 실행
    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, oAuth2User.getAttributes());


        User savedUser = userRepository.findByEmail(userInfo.getEmail()).orElse(null);

        // 이미 회원가입 된 이메일
        if(savedUser!=null) {
            if(providerType != savedUser.getProviderType()) {
                throw new OAuthProviderMissMatchException("해당 이메일로 가입 된 계정이 이미 존재합니다.");
            }
            savedUser = updateUser(savedUser, userInfo);

        } else {
            // 회원가입 되어 있지 않은 사용자
            savedUser = registerUser(userInfo, providerType);
        }
        return JwtUserDetails.create(savedUser,oAuth2User.getAttributes());
    }

    // 회원가입 진행
    @Transactional
    User registerUser(OAuth2UserInfo userInfo, ProviderType providerType) {
        User user = userRepository.save(
                User.builder()
                .email(userInfo.getEmail())
                        .name(userInfo.getName())
                .role(Role.USER)
                .imgUrl(userInfo.getImageUrl())
                .providerType(providerType)
                .build());
        System.out.println("*******회원가입 실행**********");
        return user;

    }

    // 업데이트
    @Transactional
     User updateUser(User user, OAuth2UserInfo userInfo) {
        if(userInfo.getName()!=null && !user.getName().equals(userInfo.getName())) {
            user.setName(userInfo.getName());
        }

        if(userInfo.getImageUrl()!=null && !user.getImgUrl().equals(userInfo.getImageUrl())) {
            user.setImgUrl(userInfo.getImageUrl());
        }

        System.out.println("*******업데이트 실행**********");
        return user;
    }
}
