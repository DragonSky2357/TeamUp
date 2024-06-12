package com.dragonsky.teamup.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthException extends RuntimeException{
    private final AuthErrorCode errorCode;
}
