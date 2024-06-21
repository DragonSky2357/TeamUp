package com.dragonsky.teamup.partyreview.facade;

import com.dragonsky.teamup.member.model.Member;
import com.dragonsky.teamup.party.model.Party;
import com.dragonsky.teamup.party.service.PartyService;
import com.dragonsky.teamup.partyreview.dto.request.AddPartyReviewRequest;
import com.dragonsky.teamup.partyreview.dto.request.GetPartyReviewsRequest;
import com.dragonsky.teamup.partyreview.dto.response.AddPartyReviewResponse;
import com.dragonsky.teamup.partyreview.dto.response.GetPartyReviewResponse;
import com.dragonsky.teamup.partyreview.service.PartyReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartyReviewFacade {
    private final PartyReviewService partyReviewService;
    private final PartyService partyService;

    public AddPartyReviewResponse addPartyReview(AddPartyReviewRequest request, Long partyId, Member member) {
        Party party = partyService.getParty(partyId);

        return AddPartyReviewResponse.of(partyReviewService.addPartyReview(request, party, member));
    }

    public List<GetPartyReviewResponse> getPartyReviews(GetPartyReviewsRequest request, Long partyId) {
        return GetPartyReviewResponse.of(partyReviewService.getPartyReviews(request, partyId));
    }

    public GetPartyReviewResponse getPartyReview(Long partyId, Long reviewId) {
        return GetPartyReviewResponse.of(partyReviewService.getPartyReview(partyId, reviewId));
    }
}
