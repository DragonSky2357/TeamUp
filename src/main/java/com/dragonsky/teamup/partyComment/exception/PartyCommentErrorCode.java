package com.dragonsky.teamup.partyComment.exception;

import com.dragonsky.teamup.global.exception.CommonException;
import org.springframework.http.HttpStatus;

public enum PartyCommentErrorCode implements CommonException {
    PARTY_COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "존재 하지 않은 댓글입니다"),
    PARTY_COMMENT_MODIFY_NOT_PERMISSION(HttpStatus.FORBIDDEN.value(), "댓글 수정 권한이 없습니다"),
    PARTY_COMMENT_DELETE_NOT_PERMISSION(HttpStatus.FORBIDDEN.value(), "댓글 삭제 권한이 없습니다");

    private final int status;
    private final String message;

    PartyCommentErrorCode(int status, String message) {
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
