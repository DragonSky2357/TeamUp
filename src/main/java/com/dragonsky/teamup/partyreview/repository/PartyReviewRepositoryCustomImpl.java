package com.dragonsky.teamup.partyreview.repository;

import com.dragonsky.teamup.member.model.QMember;
import com.dragonsky.teamup.partyreview.dto.request.GetPartyReviewsRequest;
import com.dragonsky.teamup.partyreview.dto.response.GetPartyReviewDto;
import com.dragonsky.teamup.partyreview.model.QPartyReview;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PartyReviewRepositoryCustomImpl implements PartyReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<GetPartyReviewDto> findPartyReviews(GetPartyReviewsRequest request, Long partyId) {
        QPartyReview partyReview = QPartyReview.partyReview;
        QMember member = QMember.member;

        return queryFactory
                .select(Projections.fields(
                        GetPartyReviewDto.class,
                        partyReview.as("partyReview"),
                        partyReview.member.as("member")
                ))
                .from(partyReview)
                .join(partyReview.member, member)
                .fetchJoin()
                .where(eqPartyId(partyId), isNotAnonymous())
                .stream().skip(request.getPage())
                .limit(request.getSize())
                .toList();
    }

    @Override
    public GetPartyReviewDto findPartyReview(Long partyId, Long reviewId) {
        QPartyReview partyReview = QPartyReview.partyReview;
        QMember member = QMember.member;

        return queryFactory
                .select(Projections.fields(
                        GetPartyReviewDto.class,
                        partyReview.as("partyReview"),
                        partyReview.member.as("member")
                ))
                .from(partyReview)
                .join(partyReview.member, member)
                .fetchJoin()
                .where(eqPartyId(partyId), eqReviewId(reviewId), isNotAnonymous())
                .fetchOne();
    }

    private BooleanExpression eqPartyId(Long partyId) {
        return partyId != null ? QPartyReview.partyReview.party.id.eq(partyId) : null;
    }

    private BooleanExpression eqReviewId(Long reviewId) {
        return reviewId != null ? QPartyReview.partyReview.id.eq(reviewId) : null;
    }

    private BooleanExpression isNotAnonymous() {
        return QPartyReview.partyReview.anonymous.isFalse();
    }
}