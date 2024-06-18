package com.dragonsky.teamup.partyComment.facade;

import com.dragonsky.teamup.member.model.Member;
import com.dragonsky.teamup.party.model.Party;
import com.dragonsky.teamup.party.service.PartyService;
import com.dragonsky.teamup.partyComment.dto.request.AddPartyCommentRequest;
import com.dragonsky.teamup.partyComment.dto.request.GetPartyCommentsRequest;
import com.dragonsky.teamup.partyComment.dto.request.ModifyPartyCommentRequest;
import com.dragonsky.teamup.partyComment.dto.response.AddPartyCommentResponse;
import com.dragonsky.teamup.partyComment.dto.response.GetPartyCommentResponse;
import com.dragonsky.teamup.partyComment.dto.response.GetPartyCommentsResponse;
import com.dragonsky.teamup.partyComment.dto.response.ModifyPartyCommentResponse;
import com.dragonsky.teamup.partyComment.service.PartyCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartyCommentFacade {
    private final PartyCommentService partyCommentService;
    private final PartyService partyService;

    public AddPartyCommentResponse addPartyComment(AddPartyCommentRequest request, Long partyId, Member member) {
        Party party = partyService.getParty(partyId);

        return AddPartyCommentResponse.of(partyCommentService.addPartyComment(request, party, member));
    }

    public List<GetPartyCommentsResponse> getPartyComments(GetPartyCommentsRequest request, Long partyId) {
        return GetPartyCommentsResponse.of(partyCommentService.getPartyComments(request, partyId));
    }

    public GetPartyCommentResponse getPartyComment(Long partyId, Long commentId) {
        return GetPartyCommentResponse.of(partyCommentService.getPartyComment(partyId, commentId));
    }

    public ModifyPartyCommentResponse modifyPartyComment(ModifyPartyCommentRequest request, Long partyId, Long commentId, Member member) {
        return ModifyPartyCommentResponse.of(partyCommentService.modifyPartyComment(request, partyId, commentId, member));
    }

    public void removePartyComment(Long partyId, Long commentId, Member member) {
        partyCommentService.removePartyComment(partyId, commentId, member);
    }
}