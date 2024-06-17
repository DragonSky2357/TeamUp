package com.dragonsky.teamup.party.repository;

import com.dragonsky.teamup.party.dto.request.GetPartyRequest;
import com.dragonsky.teamup.party.dto.response.GetPartyDto;

import java.util.List;

public interface PartyRepositoryCustom {
    List<GetPartyDto> findPartys(GetPartyRequest request);
    GetPartyDto findPartyById(Long id);
}
