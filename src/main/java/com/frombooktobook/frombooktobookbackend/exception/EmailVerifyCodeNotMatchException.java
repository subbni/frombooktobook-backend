package com.frombooktobook.frombooktobookbackend.exception;

import org.springframework.security.core.AuthenticationException;

public class EmailVerifyCodeNotMatchException extends AuthenticationException {
    public EmailVerifyCodeNotMatchException(String msg, Throwable t) {
        super(msg,t);
    }

    public EmailVerifyCodeNotMatchException(String msg) {
        super(msg);
    }
}
