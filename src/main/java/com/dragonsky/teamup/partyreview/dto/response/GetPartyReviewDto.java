package com.dragonsky.teamup.partyreview.dto.response;

import com.dragonsky.teamup.member.model.Member;
import com.dragonsky.teamup.partyreview.model.PartyReview;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class GetPartyReviewDto {
    private PartyReview partyReview;
    private Member member;
}
