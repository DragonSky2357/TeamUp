package com.dragonsky.teamup.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberException extends RuntimeException{
    private final MemberErrorCode errorCode;
}
