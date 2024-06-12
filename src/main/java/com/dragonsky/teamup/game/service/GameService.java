package com.dragonsky.teamup.game.service;

import com.dragonsky.teamup.game.dto.request.AddGameRequest;
import com.dragonsky.teamup.game.exception.GameErrorCode;
import com.dragonsky.teamup.game.exception.GameException;
import com.dragonsky.teamup.game.model.Game;
import com.dragonsky.teamup.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameService {
    private final GameRepository gameRepository;

    @Transactional
    public Game addGame(AddGameRequest request) {
        Game findGame = gameRepository.findByTitle(request.getTitle());

        if (findGame != null) {
            log.warn("{}는 이미 존재하는 게임", request.getTitle());
            throw new GameException(GameErrorCode.GAME_ALREADY_EXIST);
        }

        Game saveGame = gameRepository.save(request.toEntity());
        log.info("제작사 : {} 타이틀명 : {} 게임 등록 완료", saveGame.getTitle(), saveGame.getTitle());

        return saveGame;
    }

    public Page<Game> getGames(int page, int size) {
        return gameRepository.findAll(PageRequest.of(page, size));
    }

    public Game getGetGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() ->
                        new GameException(GameErrorCode.GAME_NOT_FOUND)
                );
    }



//    public Game getGameById(Long id) {
//        return gameRepository.findById(id)
//                .orElseThrow(() ->
//                        new GameException(ErrorCode.GAME_NOT_FOUND)
//                );
//    }
//
//    public Game getGameByTitle(String title) {
//        return gameRepository.findByTitle(title)
//                .orElseThrow(()->
//                        new GameException(ErrorCode.GAME_NOT_FOUND)
//                );
//    }

}
