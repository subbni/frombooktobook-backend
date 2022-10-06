package com.frombooktobook.frombooktobookbackend.exception;

import org.springframework.security.core.AuthenticationException;

public class OAuthProviderMissMatchException extends AuthenticationException {
    public OAuthProviderMissMatchException(String msg, Throwable t) {
        super(msg,t);
    }

    public OAuthProviderMissMatchException(String msg) {
        super(msg);
    }
}
