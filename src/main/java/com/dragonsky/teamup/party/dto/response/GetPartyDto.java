package com.dragonsky.teamup.party.dto.response;

import com.dragonsky.teamup.game.model.Game;
import com.dragonsky.teamup.member.model.Member;
import com.dragonsky.teamup.party.model.Party;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class GetPartyDto {
    private Party party;
    private Game game;
    private Member member;
}
