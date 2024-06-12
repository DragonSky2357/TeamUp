package com.dragonsky.teamup.member.dto.response;

import com.dragonsky.teamup.member.model.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetMemberResponse {
    private Long id;
    private String nickname;

    public static GetMemberResponse of(Member member) {
        return GetMemberResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .build();
    }
}
