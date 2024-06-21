package com.dragonsky.teamup.partyreview.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPartyReviewsRequest {
    private int page = 0;
    private int size = 20;
}
