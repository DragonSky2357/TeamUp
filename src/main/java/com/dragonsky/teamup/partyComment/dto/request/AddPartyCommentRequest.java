package com.dragonsky.teamup.partyComment.dto.request;

import com.dragonsky.teamup.member.model.Member;
import com.dragonsky.teamup.party.model.Party;
import com.dragonsky.teamup.partyComment.model.PartyComment;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AddPartyCommentRequest {
    @NotBlank(message = "파티 댓글은 필수 항목입니다")
    private String content;

    public PartyComment toEntity(Party party, Member member) {
        return PartyComment.builder()
                .content(content)
                .party(party)
                .member(member)
                .build();
    }
}