package com.dragonsky.teamup.party.service;

import com.dragonsky.teamup.game.model.Game;
import com.dragonsky.teamup.member.model.Member;
import com.dragonsky.teamup.party.dto.request.AddPartyRequest;
import com.dragonsky.teamup.party.dto.request.GetPartyRequest;
import com.dragonsky.teamup.party.dto.request.ModifyPartyRequest;
import com.dragonsky.teamup.party.dto.response.GetPartyDto;
import com.dragonsky.teamup.party.exception.PartyErrorCode;
import com.dragonsky.teamup.party.exception.PartyException;
import com.dragonsky.teamup.party.model.Party;
import com.dragonsky.teamup.party.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PartyService {
    private final PartyRepository partyRepository;

    @Transactional
    public Party addParty(AddPartyRequest request, Member member, Game game) {
        Party saveParty = partyRepository.save(request.toEntity(member, game));
        log.info("파티 ID : {} 게임 타이틀 : {} 파티 생성 완료", saveParty.getId(), game.getTitle());

        return saveParty;
    }

    public List<GetPartyDto> getPartys(GetPartyRequest request) {
        return partyRepository.findPartys(request);
    }

    public GetPartyDto getPartyById(Long id) {
        return partyRepository.findPartyById(id);
    }

    private Party getParty(Long id) {
        return partyRepository.findById(id)
                .orElseThrow(() -> new PartyException(PartyErrorCode.PARTY_NOT_FOUND));
    }

    @Transactional
    public Party modifyParty(Long id, ModifyPartyRequest request, Member member) {
        Party party = getParty(id);

        Member partyMember = party.getMember();
        if (!Objects.equals(partyMember.getId(), member.getId())) {
            throw new PartyException(PartyErrorCode.PARTY_MODIFY_NOT_PERMISSION);
        }

        Party.modify(request, party);

        Party modifyParty = partyRepository.save(party);
        log.info("파티 ID : {} 파티 수정 완료", party.getId());

        return modifyParty;
    }

    @Transactional
    public void removeParty(Long id, Member member) {
        Party party = getParty(id);

        Member partyMember = party.getMember();
        if (!Objects.equals(partyMember.getId(), member.getId())) {
            throw new PartyException(PartyErrorCode.PARTY_DELETE_NOT_PERMISSION);
        }

        partyRepository.delete(party);
        log.info("파티 ID : {} 파티 삭제 완료", party.getId());
    }
}
