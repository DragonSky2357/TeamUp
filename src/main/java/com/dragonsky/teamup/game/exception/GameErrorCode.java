package com.dragonsky.teamup.game.exception;

import com.dragonsky.teamup.global.exception.CommonException;
import org.springframework.http.HttpStatus;

public enum GameErrorCode implements CommonException {
    GAME_ALREADY_EXIST(HttpStatus.BAD_REQUEST.value(),"이미 존재하는 게임입니다"),
    GAME_NOT_FOUND(HttpStatus.BAD_REQUEST.value(),"존재 하지 않은 게임입니다");

    private final int status;
    private final String message;

    GameErrorCode(int status, String message){
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
