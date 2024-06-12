package com.dragonsky.teamup.game.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GameException extends RuntimeException{
    private final GameErrorCode errorCode;
}