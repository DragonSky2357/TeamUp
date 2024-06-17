package com.dragonsky.teamup.party.facade;

import com.dragonsky.teamup.game.model.Game;
import com.dragonsky.teamup.game.service.GameService;
import com.dragonsky.teamup.member.model.Member;
import com.dragonsky.teamup.party.dto.request.AddPartyRequest;
import com.dragonsky.teamup.party.dto.request.GetPartyRequest;
import com.dragonsky.teamup.party.dto.request.GetPartyResponse;
import com.dragonsky.teamup.party.dto.request.ModifyPartyRequest;
import com.dragonsky.teamup.party.dto.response.AddPartyResponse;
import com.dragonsky.teamup.party.dto.response.GetPartysResponse;
import com.dragonsky.teamup.party.dto.response.ModifyPartyResponse;
import com.dragonsky.teamup.party.service.PartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartyFacade {
    private final PartyService partyService;
    private final GameService gameService;

    public AddPartyResponse addParty(AddPartyRequest request, Member member) {
        Game game = gameService.getGameById(request.getGameId());

        return AddPartyResponse.of(partyService.addParty(request, member, game));
    }

    public List<GetPartysResponse> getPartys(GetPartyRequest request) {
        return GetPartysResponse.of(partyService.getPartys(request));
    }

    public GetPartyResponse getPartyById(Long id) {
        return GetPartyResponse.of(partyService.getPartyById(id));
    }

    public ModifyPartyResponse modifyParty(Long id, ModifyPartyRequest request, Member member) {
        return ModifyPartyResponse.of(partyService.modifyParty(id, request, member));
    }

    public void removeParty(Long id, Member member) {
        partyService.removeParty(id, member);
    }
}
