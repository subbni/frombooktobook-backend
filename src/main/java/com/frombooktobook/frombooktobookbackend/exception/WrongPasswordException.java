package com.frombooktobook.frombooktobookbackend.exception;

import lombok.Getter;

@Getter
public class WrongPasswordException extends RuntimeException{
    private String msg;

    public WrongPasswordException(String msg) {
        super(msg);
    }
}
