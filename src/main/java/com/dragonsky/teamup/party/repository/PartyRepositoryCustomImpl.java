package com.dragonsky.teamup.party.repository;

import com.dragonsky.teamup.game.model.QGame;
import com.dragonsky.teamup.member.model.QMember;
import com.dragonsky.teamup.party.dto.request.GetPartyRequest;
import com.dragonsky.teamup.party.dto.response.GetPartyDto;
import com.dragonsky.teamup.party.model.QParty;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class PartyRepositoryCustomImpl implements PartyRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<GetPartyDto> findPartys(GetPartyRequest request) {
        QParty party = QParty.party;
        QGame game = QGame.game;
        QMember member = QMember.member;

        return queryFactory
                .select(Projections.fields(
                        GetPartyDto.class,
                        party,
                        party.game.as("game"),
                        party.member.as("member")
                ))
                .from(party)
                .join(party.game, game)
                .join(party.member, member)
                .fetchJoin()
                .stream().skip(request.getPage())
                .limit(request.getSize())
                .toList();
    }

    @Override
    public GetPartyDto findPartyById(Long id) {
        QParty party = QParty.party;
        QGame game = QGame.game;
        QMember member = QMember.member;

        return queryFactory
                .select(Projections.fields(
                        GetPartyDto.class,
                        party,
                        party.game.as("game"),
                        party.member.as("member")
                ))
                .from(party)
                .where(eqId(id))
                .join(party.game, game)
                .join(party.member, member)
                .fetchJoin()
                .fetchOne();
    }

    private BooleanExpression eqId(Long id) {
        return id != null ? QParty.party.id.eq(id) : null;
    }
}
