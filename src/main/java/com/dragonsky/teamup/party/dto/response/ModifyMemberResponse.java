package com.dragonsky.teamup.party.dto.response;

import com.dragonsky.teamup.member.model.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ModifyMemberResponse {
    private Long id;
    private String nickname;

    public static ModifyMemberResponse of(Member member) {
        return ModifyMemberResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .build();
    }
}
