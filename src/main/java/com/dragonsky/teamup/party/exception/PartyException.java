package com.dragonsky.teamup.party.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PartyException extends RuntimeException {
    private final PartyErrorCode errorCode;
}