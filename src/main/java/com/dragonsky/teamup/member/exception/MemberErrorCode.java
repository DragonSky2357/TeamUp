package com.dragonsky.teamup.member.exception;

import com.dragonsky.teamup.global.exception.CommonException;
import org.springframework.http.HttpStatus;

public enum MemberErrorCode implements CommonException {
    DUPLICATED_MEMBER(HttpStatus.BAD_REQUEST.value(),"이미 존재하는 멤버입니다"),
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(),"존재 하지 않은 멤버입니다");

    private final int status;
    private final String message;

    MemberErrorCode(int status,String message){
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
