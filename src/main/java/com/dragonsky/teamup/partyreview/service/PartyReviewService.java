package com.dragonsky.teamup.partyreview.service;

import com.dragonsky.teamup.member.model.Member;
import com.dragonsky.teamup.party.model.Party;
import com.dragonsky.teamup.partyreview.dto.request.AddPartyReviewRequest;
import com.dragonsky.teamup.partyreview.dto.request.GetPartyReviewsRequest;
import com.dragonsky.teamup.partyreview.dto.response.GetPartyReviewDto;
import com.dragonsky.teamup.partyreview.model.PartyReview;
import com.dragonsky.teamup.partyreview.repository.PartyReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PartyReviewService {
    private final PartyReviewRepository partyReviewRepository;

    @Transactional
    public PartyReview addPartyReview(AddPartyReviewRequest request, Party party, Member member) {
        PartyReview savePartyReview = partyReviewRepository.save(request.toEntity(party, member));
        log.info("파티 리뷰 ID : {} 파티 ID : {} 파티 리뷰 생성 완료", savePartyReview.getId(), party.getId());

        return savePartyReview;
    }

    public List<GetPartyReviewDto> getPartyReviews(GetPartyReviewsRequest request, Long partyId) {
        return partyReviewRepository.findPartyReviews(request, partyId);
    }

    public GetPartyReviewDto getPartyReview(Long partyId, Long reviewId) {
        return partyReviewRepository.findPartyReview(partyId, reviewId);
    }
}