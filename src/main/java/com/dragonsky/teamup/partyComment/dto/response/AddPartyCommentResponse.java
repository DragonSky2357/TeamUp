package com.dragonsky.teamup.partyComment.dto.response;

import com.dragonsky.teamup.partyComment.model.PartyComment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AddPartyCommentResponse {
    private Long id;
    private String content;

    public static AddPartyCommentResponse of(PartyComment partyComment) {
        return AddPartyCommentResponse.builder()
                .id(partyComment.getId())
                .content(partyComment.getContent())
                .build();
    }
}