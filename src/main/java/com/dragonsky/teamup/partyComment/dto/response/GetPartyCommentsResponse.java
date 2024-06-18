package com.dragonsky.teamup.partyComment.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class GetPartyCommentsResponse {
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

    public static List<GetPartyCommentsResponse> of(List<GetPartyCommentDto> getPartyCommentDtos) {
        return getPartyCommentDtos.stream()
                .map(GetPartyCommentsResponse::mapToGetPartyCommentResponse)
                .collect(Collectors.toList());
    }

    private static GetPartyCommentsResponse mapToGetPartyCommentResponse(GetPartyCommentDto getPartyCommentDto) {
        return GetPartyCommentsResponse.builder()
                .comment(mapToComment(getPartyCommentDto.getPartyComment()))
                .member(mapToMember(getPartyCommentDto.getMember()))
                .build();
    }

    private static Comment mapToComment(com.dragonsky.teamup.partyComment.model.PartyComment partyComment) {
        return Comment.builder()
                .id(partyComment.getId())
                .content(partyComment.getContent())
                .createdAt(partyComment.getCreatedAt())
                .build();
    }

    private static Member mapToMember(com.dragonsky.teamup.member.model.Member member) {
        return Member.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .build();
    }
}