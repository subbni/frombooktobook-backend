package com.frombooktobook.frombooktobookbackend.security.userinfo;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo{

    private Map<String, Object> properties;
    public KakaoOAuth2UserInfo(Map<String,Object> attributes) {
        super(attributes);
        properties = (Map<String, Object>) attributes.get("properties");
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getEmail() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("kakao_account");
        if(properties==null) {
            return null;
        }
        return (String) properties.get("email");
    }

    @Override
    public String getImageUrl() {
        if(properties==null) {
            return null;
        }
        return (String) properties.get("thumbnail_image");
    }

    @Override
    public String getName() {
        if(properties==null) {
            return null;
        }
        return (String) properties.get("nickname");
    }
}
