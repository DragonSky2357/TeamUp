package com.dragonsky.teamup.party.dto.request;

import com.dragonsky.teamup.party.dto.response.GetPartyDto;
import com.dragonsky.teamup.party.model.PartyStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GetPartyResponse {
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

    public static GetPartyResponse of(GetPartyDto getPartyDto) {
        return mapToGetPartyResponse(getPartyDto);
    }

    private static GetPartyResponse mapToGetPartyResponse(GetPartyDto party) {
        return GetPartyResponse.builder()
                .party(mapToParty(party.getParty()))
                .game(mapToGame(party.getGame()))
                .member(mapToMember(party.getMember()))
                .build();
    }

    private static GetPartyResponse.Party mapToParty(com.dragonsky.teamup.party.model.Party party) {
        return GetPartyResponse.Party.builder()
                .id(party.getId())
                .title(party.getTitle())
                .people(party.getPeople())
                .status(party.getStatus())
                .view(party.getView())
                .deadline(party.getDeadline())
                .build();
    }

    private static GetPartyResponse.Game mapToGame(com.dragonsky.teamup.game.model.Game gameDto) {
        return GetPartyResponse.Game.builder()
                .id(gameDto.getId())
                .logo(gameDto.getLogo())
                .build();
    }

    private static GetPartyResponse.Member mapToMember(com.dragonsky.teamup.member.model.Member memberDto) {
        return GetPartyResponse.Member.builder()
                .id(memberDto.getId())
                .nickname(memberDto.getNickname())
                .build();
    }
}
