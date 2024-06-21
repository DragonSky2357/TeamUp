package com.dragonsky.teamup.partyreview.dto.request;

import com.dragonsky.teamup.member.model.Member;
import com.dragonsky.teamup.party.model.Party;
import com.dragonsky.teamup.partyreview.model.PartyReview;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AddPartyReviewRequest {
    @NotBlank(message = "내용은 필수 항목입니다")
    private String content;

    @Positive(message = "평점은 1점 이상이어야 합니다")
    private Integer rating;

    @NotNull(message = "익명 여부는 필수 항목입니다")
    private Boolean anonymous;

    public PartyReview toEntity(Party party, Member member) {
        return PartyReview.builder()
                .content(content)
                .rating(rating)
                .anonymous(anonymous)
                .likes(0)
                .party(party)
                .member(member)
                .build();
    }
}
