package com.dragonsky.teamup.game.repository;

import com.dragonsky.teamup.game.dto.request.GameSearchRequest;
import com.dragonsky.teamup.game.model.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GameRepository extends JpaRepository<Game, Long> {
    Page<Game> findAll(Pageable pageable);
    Game findByTitle(String title);

    @Query(value = "SELECT * FROM game g WHERE g.title LIKE :title%", nativeQuery = true)
    Page<Game> findAllBySearch(String title, Pageable pageable);
}
