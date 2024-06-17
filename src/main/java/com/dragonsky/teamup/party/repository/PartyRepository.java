package com.dragonsky.teamup.party.repository;

import com.dragonsky.teamup.party.model.Party;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartyRepository extends JpaRepository<Party, Long>, PartyRepositoryCustom {
    @EntityGraph(attributePaths = {"game", "member"})
    Optional<Party> findById(Long id);
}
