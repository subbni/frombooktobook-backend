package com.frombooktobook.frombooktobookbackend.security.user;

import java.util.Map;


public abstract class OAuth2UserInfo {
    // 키-값 쌍의 일반 Map에서 사용자의 필수 세부 사항을 가져오는데 사용

    protected Map<String,Object> attributes;

    public OAuth2UserInfo(Map<String,Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();
    public abstract String getEmail();
    public abstract String getImageUrl();
    public abstract String getName();
}
