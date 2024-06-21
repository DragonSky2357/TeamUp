package com.dragonsky.teamup.partyreview.repository;

import com.dragonsky.teamup.partyreview.dto.request.GetPartyReviewsRequest;
import com.dragonsky.teamup.partyreview.dto.response.GetPartyReviewDto;

import java.util.List;

public interface PartyReviewRepositoryCustom {
    List<GetPartyReviewDto> findPartyReviews(GetPartyReviewsRequest request, Long partyId);
    GetPartyReviewDto findPartyReview(Long partyId, Long reviewId);
}