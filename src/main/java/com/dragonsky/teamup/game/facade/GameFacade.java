package com.dragonsky.teamup.game.facade;

import com.dragonsky.teamup.game.dto.request.AddGameRequest;
import com.dragonsky.teamup.game.dto.request.GameSearchRequest;
import com.dragonsky.teamup.game.dto.request.GetGamesRequest;
import com.dragonsky.teamup.game.dto.request.ModifyGameRequest;
import com.dragonsky.teamup.game.dto.response.*;
import com.dragonsky.teamup.game.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameFacade {
    private final GameService gameService;

    public AddGameResponse addGame(AddGameRequest request) {
        return AddGameResponse.of(gameService.addGame(request));
    }

    public GetGameByIdResponse getGameById(Long id) {
        return GetGameByIdResponse.of(gameService.getGameById(id));
    }

    public List<GetGamesResponse> getGames(GetGamesRequest request) {
        return GetGamesResponse.of(gameService.getGames(request));
    }

    public List<GetGamesBySearchResponse> getGameBySearch(GameSearchRequest request) {
        return GetGamesBySearchResponse.of(gameService.getGamesBySearch(request));
    }

    public ModifyGameResponse modifyGame(Long id,ModifyGameRequest request) {
        return ModifyGameResponse.of(gameService.modifyGame(id,request));
    }

    public void removeGame(Long id) {
        gameService.removeGame(id);
    }
}
