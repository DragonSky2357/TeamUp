package com.dragonsky.teamup.game.controller;

import com.dragonsky.teamup.game.dto.request.AddGameRequest;
import com.dragonsky.teamup.game.dto.response.AddGameResponse;
import com.dragonsky.teamup.game.dto.response.GetGameByIdResponse;
import com.dragonsky.teamup.game.dto.response.GetGamesResponse;
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
    ){
        return ResponseEntity.ok(gameFacade.addGame(request));
    }

    @GetMapping()
    public ResponseEntity<List<GetGamesResponse>> getGames(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        return ResponseEntity.ok(gameFacade.getGames(page,size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetGameByIdResponse> getGameById(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(gameFacade.getGameById(id));
    }

}
