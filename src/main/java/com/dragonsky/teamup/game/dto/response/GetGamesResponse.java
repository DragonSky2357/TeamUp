package com.dragonsky.teamup.game.dto.response;

import com.dragonsky.teamup.game.model.Game;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class GetGamesResponse {
    private long id;
    private String title;
    private String producer;
    private String logo;

    public static List<GetGamesResponse> of(Page<Game> games) {
        return games.stream()
                .map(game -> GetGamesResponse.builder()
                        .id(game.getId())
                        .title(game.getTitle())
                        .producer(game.getProducer())
                        .logo(game.getLogo())
                        .build())
                .collect(Collectors.toList());
    }
}
