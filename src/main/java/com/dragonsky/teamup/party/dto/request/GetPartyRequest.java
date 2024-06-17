package com.dragonsky.teamup.party.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPartyRequest {
    private int page = 0;
    private int size = 20;
}
