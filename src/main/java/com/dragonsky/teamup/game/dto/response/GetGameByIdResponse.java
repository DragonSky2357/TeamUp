package com.dragonsky.teamup.game.dto.response;

import com.dragonsky.teamup.game.model.Game;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetGameByIdResponse {
    private Long id;
    private String title;
    private String producer;
    private String logo;

    public static GetGameByIdResponse of(Game game){
        return GetGameByIdResponse.builder()
                .id(game.getId())
                .title(game.getTitle())
                .producer(game.getProducer())
                .logo(game.getLogo())
                .build();
    }
}
