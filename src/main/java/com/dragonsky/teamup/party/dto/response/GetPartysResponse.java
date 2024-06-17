package com.dragonsky.teamup.party.dto.response;

import com.dragonsky.teamup.party.model.PartyStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class GetPartysResponse {
    private Party party;
    private Game game;
    private Member member;

    @Getter
    @Builder
    public static class Party {
        private Long id;
        private String title;
        private Integer people;
        private PartyStatus status;
        private Integer view;
        private LocalDateTime deadline;
    }

    @Getter
    @Builder
    public static class Game {
        private Long id;
        private String logo;
    }

    @Getter
    @Builder
    public static class Member {
        private Long id;
        private String nickname;
    }

    public static List<GetPartysResponse> of(List<GetPartyDto> getPartyDtos) {
        return getPartyDtos.stream()
                .map(GetPartysResponse::mapToGetPartysResponse)
                .collect(Collectors.toList());
    }

    private static GetPartysResponse mapToGetPartysResponse(GetPartyDto party) {
        return GetPartysResponse.builder()
                .party(mapToParty(party.getParty()))
                .game(mapToGame(party.getGame()))
                .member(mapToMember(party.getMember()))
                .build();
    }

    private static Party mapToParty(com.dragonsky.teamup.party.model.Party party) {
        return Party.builder()
                .id(party.getId())
                .title(party.getTitle())
                .people(party.getPeople())
                .status(party.getStatus())
                .view(party.getView())
                .deadline(party.getDeadline())
                .build();
    }

    private static Game mapToGame(com.dragonsky.teamup.game.model.Game gameDto) {

        return Game.builder()
                .id(gameDto.getId())
                .logo(gameDto.getLogo())
                .build();
    }

    private static Member mapToMember(com.dragonsky.teamup.member.model.Member memberDto) {
        return Member.builder()
                .id(memberDto.getId())
                .nickname(memberDto.getNickname())
                .build();
    }
}
