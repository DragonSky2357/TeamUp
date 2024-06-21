package com.dragonsky.teamup.partyreview.dto.response;

import com.dragonsky.teamup.partyreview.model.PartyReview;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AddPartyReviewResponse {
    private Long id;
    private String content;
    private int rating;
    private boolean anonymous;
    private Long partyId;

    public static AddPartyReviewResponse of(PartyReview partyReview) {
        return AddPartyReviewResponse.builder()
                .id(partyReview.getId())
                .content(partyReview.getContent())
                .rating(partyReview.getRating())
                .anonymous(partyReview.getAnonymous())
                .partyId(partyReview.getParty().getId())
                .build();
    }
}
