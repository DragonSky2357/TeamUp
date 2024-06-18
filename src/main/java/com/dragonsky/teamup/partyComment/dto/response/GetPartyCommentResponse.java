package com.dragonsky.teamup.partyComment.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GetPartyCommentResponse {
    private Comment comment;
    private Member member;

    @Getter
    @Builder
    public static class Comment {
        private Long id;
        private String content;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    public static class Member {
        private Long id;
        private String nickname;
    }

    public static GetPartyCommentResponse of(GetPartyCommentDto partyCommentDto) {
        return mapToGetPartyCommentResponse(partyCommentDto);
    }

    private static GetPartyCommentResponse mapToGetPartyCommentResponse(GetPartyCommentDto getPartyCommentDto) {
        return GetPartyCommentResponse.builder()
                .comment(mapToComment(getPartyCommentDto.getPartyComment()))
                .member(mapToMember(getPartyCommentDto.getMember()))
                .build();
    }

    private static Comment mapToComment(com.dragonsky.teamup.partyComment.model.PartyComment partyComment) {
        return GetPartyCommentResponse.Comment.builder()
                .id(partyComment.getId())
                .content(partyComment.getContent())
                .createdAt(partyComment.getCreatedAt())
                .build();
    }

    private static Member mapToMember(com.dragonsky.teamup.member.model.Member member) {
        return GetPartyCommentResponse.Member.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .build();
    }
}