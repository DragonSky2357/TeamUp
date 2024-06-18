package com.dragonsky.teamup.partyComment.repository;

import com.dragonsky.teamup.partyComment.model.PartyComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartyCommentRepository extends JpaRepository<PartyComment, Long>, PartyCommentRepositoryCustom {
    Optional<PartyComment> findByPartyIdAndId(Long partyId, Long commentId);
}