package com.dragonsky.teamup.partyreview.repository;

import com.dragonsky.teamup.partyreview.model.PartyReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyReviewRepository extends JpaRepository<PartyReview, Long>, PartyReviewRepositoryCustom {
}
