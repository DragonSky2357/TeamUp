package com.dragonsky.teamup.auth.exception;

import com.dragonsky.teamup.global.exception.CommonException;
import org.springframework.http.HttpStatus;

public enum AuthErrorCode implements CommonException {
    BAD_CREDENTIAL(HttpStatus.UNAUTHORIZED.value(),"아이디 또는 비밀번호 오류입니다");

    private final int status;
    private final String message;

    AuthErrorCode(int status,String message){
        this.status = status;
        this.message = message;
    }

    @Override
    public int getStatus() {
        return this.status;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String getError() {
        return this.name();
    }
}
