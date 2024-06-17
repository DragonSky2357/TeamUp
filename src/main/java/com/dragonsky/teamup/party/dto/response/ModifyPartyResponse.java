package com.dragonsky.teamup.party.dto.response;

import com.dragonsky.teamup.party.model.PartyStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ModifyPartyResponse {
    private Party party;
    private Game game;
    private Member member;

    @Getter
    @Builder
    public static class Party {
        private Long id;
        private String title;
        private String content;
        private Integer people;
        private PartyStatus status;
        private Integer view;
        private LocalDateTime deadline;
    }

    @Getter
    @Builder
    public static class Game {
        private Long id;
        private String title;
        private String logo;
    }

    @Getter
    @Builder
    public static class Member {
        private Long id;
        private String nickname;
    }

    public static ModifyPartyResponse of(com.dragonsky.teamup.party.model.Party party) {
        return ModifyPartyResponse.builder()
                .party(Party.builder()
                        .id(party.getId())
                        .title(party.getTitle())
                        .content(party.getContent())
                        .people(party.getPeople())
                        .status(party.getStatus())
                        .view(party.getView())
                        .deadline(party.getDeadline())
                        .build())
                .game(Game.builder()
                        .id(party.getGame().getId())
                        .title(party.getGame().getTitle())
                        .logo(party.getGame().getLogo())
                        .build())
                .member(Member.builder()
                        .id(party.getMember().getId())
                        .nickname(party.getMember().getNickname())
                        .build())
                .build();
    }

}
