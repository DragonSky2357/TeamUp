package com.dragonsky.teamup.party.exception;

import com.dragonsky.teamup.global.exception.CommonException;
import org.springframework.http.HttpStatus;

public enum PartyErrorCode implements CommonException {
    PARTY_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "존재 하지 않은 파티입니다"),
    PARTY_MODIFY_NOT_PERMISSION(HttpStatus.FORBIDDEN.value(), "파티 수정 권한이 없습니다"),
    PARTY_DELETE_NOT_PERMISSION(HttpStatus.FORBIDDEN.value(), "파티 삭제 권한이 없습니다");

    private final int status;
    private final String message;

    PartyErrorCode(int status, String message) {
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
