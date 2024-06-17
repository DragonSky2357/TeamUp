package com.dragonsky.teamup.party.dto.request;

import com.dragonsky.teamup.party.model.PartyStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ModifyPartyRequest {
    private String title;
    private String content;
    private Integer people;
    private PartyStatus status;
    private LocalDateTime deadline;
}
