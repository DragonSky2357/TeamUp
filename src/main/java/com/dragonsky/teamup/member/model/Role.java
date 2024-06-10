package com.dragonsky.teamup.member.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST","손님"),
    MEMBER("ROLE_MEMBER","멤버"),
    ADMIN("ROLE_ADMIN","관리자");

    private final String key;
    private final String value;
}
