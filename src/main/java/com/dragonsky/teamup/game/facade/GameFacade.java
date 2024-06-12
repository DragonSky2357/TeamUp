package com.dragonsky.teamup.game.facade;

import com.dragonsky.teamup.game.dto.request.AddGameRequest;
import com.dragonsky.teamup.game.dto.response.AddGameResponse;
import com.dragonsky.teamup.game.dto.response.GetGameByIdResponse;
import com.dragonsky.teamup.game.dto.response.GetGamesResponse;
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
        return GetGameByIdResponse.of(gameService.getGetGameById(id));
    }

    public List<GetGamesResponse> getGames(int page, int size) {
        return GetGamesResponse.of(gameService.getGames(page, size));
    }

//    public GetMemberResponse getMember(Long id) {
//        return GetMemberResponse.of(gameService.getMember(id));
//    }
//
//    public ModifyMemberResponse modifyMember(Long id, ModifyMemberRequest request) {
//        return ModifyMemberResponse.of(gameService.modifyMember(id, request));
//    }
//
//    public void removeMember(Long id) {
//        gameService.removeMember(id);
//    }
}
