package com.dragonsky.teamup.game.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ModifyGameRequest {
    private String title;
    private String producer;
    private String logo;
}
