package com.dragonsky.teamup.party.dto.response;

import com.dragonsky.teamup.party.model.Party;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AddPartyResponse {
    private Long id;
    private String title;
    private Integer people;
    private LocalDateTime deadline;
    private LocalDateTime createDate;

    public static AddPartyResponse of(Party party) {
        return AddPartyResponse.builder()
                .id(party.getId())
                .title(party.getTitle())
                .people(party.getPeople())
                .deadline(party.getDeadline())
                .createDate(party.getCreatedAt())
                .build();
    }
}
