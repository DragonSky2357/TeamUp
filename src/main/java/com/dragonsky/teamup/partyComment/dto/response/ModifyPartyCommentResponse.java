package com.dragonsky.teamup.partyComment.dto.response;

import com.dragonsky.teamup.partyComment.model.PartyComment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ModifyPartyCommentResponse {
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

    public static ModifyPartyCommentResponse of(PartyComment partyComment) {
        return mapToModifyPartyCommentResponse(partyComment);
    }

    private static ModifyPartyCommentResponse mapToModifyPartyCommentResponse(PartyComment partyComment) {
        return ModifyPartyCommentResponse.builder()
                .comment(mapToComment(partyComment))
                .member(mapToMember(partyComment.getMember()))
                .build();
    }

    private static ModifyPartyCommentResponse.Comment mapToComment(com.dragonsky.teamup.partyComment.model.PartyComment partyComment) {
        return Comment.builder()
                .id(partyComment.getId())
                .content(partyComment.getContent())
                .createdAt(partyComment.getCreatedAt())
                .build();
    }

    private static ModifyPartyCommentResponse.Member mapToMember(com.dragonsky.teamup.member.model.Member member) {
        return ModifyPartyCommentResponse.Member.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .build();
    }
}