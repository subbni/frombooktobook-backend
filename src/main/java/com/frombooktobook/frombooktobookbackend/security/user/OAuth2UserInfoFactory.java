package com.frombooktobook.frombooktobookbackend.security.user;

import com.frombooktobook.frombooktobookbackend.domain.user.ProviderType;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String,Object> attributes) {
        switch(providerType) {
            case GOOGLE: return new GoogleOAuth2UserInfo(attributes);
            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}
