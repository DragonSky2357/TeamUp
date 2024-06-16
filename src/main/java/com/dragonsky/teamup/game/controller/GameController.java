package com.dragonsky.teamup.game.controller;

import com.dragonsky.teamup.game.dto.request.AddGameRequest;
import com.dragonsky.teamup.game.dto.request.GameSearchRequest;
import com.dragonsky.teamup.game.dto.request.GetGamesRequest;
import com.dragonsky.teamup.game.dto.request.ModifyGameRequest;
import com.dragonsky.teamup.game.dto.response.*;
import com.dragonsky.teamup.game.facade.GameFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/games")
public class GameController {
    private final GameFacade gameFacade;

    @PostMapping()
    public ResponseEntity<AddGameResponse> addGame(
            @Valid @RequestBody AddGameRequest request
    ) {
        return ResponseEntity.ok(gameFacade.addGame(request));
    }

    @GetMapping()
    public ResponseEntity<List<GetGamesResponse>> getGames(
            @ModelAttribute GetGamesRequest request
    ) {
        return ResponseEntity.ok(gameFacade.getGames(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetGameByIdResponse> getGameById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(gameFacade.getGameById(id));
    }

    // 현재 JPA로 검색 이후 Elastic Search로 변경 예정
    @GetMapping("/search")
    public ResponseEntity<List<GetGamesBySearchResponse>> getGamesBySearch(
            @ModelAttribute GameSearchRequest request
    ) {
        return ResponseEntity.ok(gameFacade.getGameBySearch(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModifyGameResponse> modifyGame(
            @PathVariable Long id,
            @RequestBody ModifyGameRequest request
    ) {
        return ResponseEntity.ok(gameFacade.modifyGame(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeGame(
            @PathVariable Long id
    ) {
        gameFacade.removeGame(id);
        return ResponseEntity.ok().build();
    }
}
