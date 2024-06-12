package com.dragonsky.teamup.game.dto.request;

import com.dragonsky.teamup.game.model.Game;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AddGameRequest {
    @NotBlank(message = "게임 이름은 필수 항목입니다")
    private String title;

    @NotBlank(message = "게임 제작는 필수 항목입니다 ")
    private String producer;

    private String logo;

    public Game toEntity(){
        return Game.builder()
                .title(title)
                .producer(producer)
                .logo(logo)
                .build();
    }
}
