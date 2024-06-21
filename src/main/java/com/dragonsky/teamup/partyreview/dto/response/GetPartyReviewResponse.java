package com.dragonsky.teamup.partyreview.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class GetPartyReviewResponse {
    private Review review;
    private Member member;

    @Getter
    @Builder
    public static class Review {
        private Long id;
        private String content;
        private int rating;
        private int likes;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    public static class Member {
        private Long id;
        private String nickname;
    }

    public static List<GetPartyReviewResponse> of(List<GetPartyReviewDto> partyReviewDtos) {
        return partyReviewDtos.stream()
                .map(GetPartyReviewResponse::maptoGetPartyReviewResponse)
                .collect(Collectors.toList());
    }

    public static GetPartyReviewResponse of(GetPartyReviewDto partyReview) {
        return GetPartyReviewResponse.builder()
                .review(mapToReview(partyReview.getPartyReview()))
                .member(mapToMember(partyReview.getMember()))
                .build();
    }

    private static GetPartyReviewResponse maptoGetPartyReviewResponse(GetPartyReviewDto getPartyReviewDto) {
        return GetPartyReviewResponse.builder()
                .review(mapToReview(getPartyReviewDto.getPartyReview()))
                .member(mapToMember(getPartyReviewDto.getMember()))
                .build();
    }

    private static Review mapToReview(com.dragonsky.teamup.partyreview.model.PartyReview partyReview) {
        return Review.builder()
                .id(partyReview.getId())
                .content(partyReview.getContent())
                .rating(partyReview.getRating())
                .likes(partyReview.getLikes())
                .createdAt(partyReview.getCreatedAt())
                .build();
    }

    private static Member mapToMember(com.dragonsky.teamup.member.model.Member member) {
        return Member.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .build();
    }
}
