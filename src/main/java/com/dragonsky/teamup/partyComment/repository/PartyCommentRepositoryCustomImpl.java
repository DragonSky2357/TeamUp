package com.dragonsky.teamup.partyComment.repository;

import com.dragonsky.teamup.member.model.QMember;
import com.dragonsky.teamup.partyComment.dto.request.GetPartyCommentsRequest;
import com.dragonsky.teamup.partyComment.dto.response.GetPartyCommentDto;
import com.dragonsky.teamup.partyComment.model.QPartyComment;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class PartyCommentRepositoryCustomImpl implements PartyCommentRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<GetPartyCommentDto> findPartyComments(GetPartyCommentsRequest request, Long partyId) {
        QPartyComment partyComment = QPartyComment.partyComment;
        QMember member = QMember.member;

        return queryFactory
                .select(Projections.fields(
                        GetPartyCommentDto.class,
                        partyComment.as("partyComment"),
                        partyComment.member.as("member")
                ))
                .from(partyComment)
                .join(partyComment.member, member)
                .fetchJoin()
                .where(eqPartyId(partyId))
                .stream().skip(request.getPage())
                .limit(request.getSize())
                .toList();
    }

    @Override
    public GetPartyCommentDto findPartyComment(Long partyId, Long commentId) {
        QPartyComment partyComment = QPartyComment.partyComment;
        QMember member = QMember.member;

        return queryFactory
                .select(Projections.fields(
                        GetPartyCommentDto.class,
                        partyComment.as("partyComment"),
                        partyComment.member.as("member")
                ))
                .from(partyComment)
                .join(partyComment.member, member)
                .fetchJoin()
                .where(eqPartyId(partyId), eqCommentId(commentId))
                .fetchOne();
    }


    private BooleanExpression eqPartyId(Long partyId) {
        return partyId != null ? QPartyComment.partyComment.party.id.eq(partyId) : null;
    }

    private BooleanExpression eqCommentId(Long commentId) {
        return commentId != null ? QPartyComment.partyComment.id.eq(commentId) : null;
    }
}