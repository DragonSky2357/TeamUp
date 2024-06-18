package com.dragonsky.teamup.partyComment.dto.response;

import com.dragonsky.teamup.member.model.Member;
import com.dragonsky.teamup.partyComment.model.PartyComment;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class GetPartyCommentDto {
    private PartyComment partyComment;
    private Member member;
}