package com.dragonsky.teamup.auth.exception;

public enum ErrorCode {
    DUPLICATED_MEMBER("이미 존재하는 멤버입니다");

    public final String message;

    ErrorCode(String message){
        this.message = message;
    }
}
