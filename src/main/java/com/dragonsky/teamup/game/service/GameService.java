package com.dragonsky.teamup.game.service;

import com.dragonsky.teamup.game.dto.request.AddGameRequest;
import com.dragonsky.teamup.game.dto.request.GameSearchRequest;
import com.dragonsky.teamup.game.dto.request.GetGamesRequest;
import com.dragonsky.teamup.game.dto.request.ModifyGameRequest;
import com.dragonsky.teamup.game.exception.GameErrorCode;
import com.dragonsky.teamup.game.exception.GameException;
import com.dragonsky.teamup.game.model.Game;
import com.dragonsky.teamup.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        log.info("게임 : {} 제작사 : {} 게임 등록 완료", saveGame.getTitle(), saveGame.getTitle());

        return saveGame;
    }

    public Page<Game> getGames(GetGamesRequest request) {
        return gameRepository.findAll(PageRequest.of(request.getPage(), request.getSize()));
    }

    public Game getGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() ->
                        new GameException(GameErrorCode.GAME_NOT_FOUND)
                );
    }

    @Transactional
    public Game modifyGame(Long id, ModifyGameRequest request) {
        Game game = this.getGameById(id);

        Game.modify(game, request);

        log.info("게임 : {} 제작사 : {} 게임 정보 업데이트", game.getTitle(), game.getProducer());

        return gameRepository.save(game);
    }

    @Transactional
    public void removeGame(Long id) {
        Game game = this.getGameById(id);

        log.info("게임 : {} 제작사 : {} 게임 정보 삭제", game.getTitle(), game.getProducer());

        gameRepository.delete(game);
    }

    public Page<Game> getGamesBySearch(GameSearchRequest request) {
        String title = request.getTitle();
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by("title").ascending());
        return gameRepository.findAllBySearch(title, pageable);
    }
}
