package com.dragonsky.teamup.game.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameSearchRequest {
    private String title = "";
    private int page = 0;
    private int size = 20;
}
