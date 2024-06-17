package com.dragonsky.teamup.party.dto.request;

import com.dragonsky.teamup.game.model.Game;
import com.dragonsky.teamup.member.model.Member;
import com.dragonsky.teamup.party.model.Party;
import com.dragonsky.teamup.party.model.PartyStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AddPartyRequest {
    @NotBlank(message = "파티 제목은 필수 항목입니다")
    private String title;

    private String content;

    @Positive(message = "인원 수는 필수 항목입니다")
    private Integer people;

    private LocalDateTime deadline;

    private Long gameId;

    public Party toEntity(Member member, Game game) {
        return Party.builder()
                .title(title)
                .content(content)
                .people(people)
                .deadline(deadline)
                .status(PartyStatus.OPEN)
                .view(0)
                .member(member)
                .game(game)
                .build();
    }
}
