package com.dragonsky.teamup.partyComment.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPartyCommentsRequest {
    private int page = 0;
    private int size = 20;
}