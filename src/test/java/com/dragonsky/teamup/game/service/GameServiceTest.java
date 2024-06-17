package com.dragonsky.teamup.game.service;

import com.dragonsky.teamup.game.dto.request.AddGameRequest;
import com.dragonsky.teamup.game.dto.request.GameSearchRequest;
import com.dragonsky.teamup.game.dto.request.GetGamesRequest;
import com.dragonsky.teamup.game.dto.request.ModifyGameRequest;
import com.dragonsky.teamup.game.exception.GameException;
import com.dragonsky.teamup.game.model.Game;
import com.dragonsky.teamup.game.repository.GameRepository;
import com.dragonsky.teamup.global.util.cookie.CookieUtil;
import com.dragonsky.teamup.global.util.jwt.JWTUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class GameServiceTest {

    @Autowired
    private GameService gameService;

    @Autowired
    private GameRepository gameRepository;

    @MockBean
    private CookieUtil cookieUtil;

    @MockBean
    private JWTUtil jwtUtil;

    @Test
    @DisplayName("게임 등록 테스트")
    @Transactional
    void testAddGame() {
        AddGameRequest request = AddGameRequest.builder()
                .title("Test Game")
                .producer("Test Producer")
                .logo("test.png")
                .build();

        Game saveGame = gameService.addGame(request);

        assertNotNull(saveGame.getId());
        assertEquals("Test Game", saveGame.getTitle());
        assertEquals("Test Producer", saveGame.getProducer());
    }

    @Test
    @DisplayName("중복 게임 등록 테스트")
    @Transactional
    void testDuplicatedAddGame() {
        // given
        Game game = Game.builder()
                .title("Duplicated Game")
                .producer("Duplicated Producer")
                .logo("Duplicated.png")
                .build();

        gameRepository.save(game);

        AddGameRequest request = AddGameRequest.builder()
                .title("Duplicated Game")
                .producer("Duplicated Producer")
                .logo("Duplicated.png")
                .build();

        // when & then
        assertThrows(GameException.class, () ->
                gameService.addGame(request));
    }

    @Test
    @DisplayName("게임 리스트 조회 테스트")
    @Transactional
    void testGetGames() {
        // given
        Game game1 = Game.builder()
                .title("Game 1")
                .producer("Producer 1")
                .logo("Game 1.png")
                .build();

        Game game2 = Game.builder()
                .title("Game 2")
                .producer("Producer 2")
                .logo("Game 2.png")
                .build();

        Game game3 = Game.builder()
                .title("Game 3")
                .producer("Producer 3")
                .logo("Game 3.png")
                .build();

        gameRepository.save(game1);
        gameRepository.save(game2);
        gameRepository.save(game3);

        GetGamesRequest request = GetGamesRequest.builder()
                .page(0)
                .size(2)
                .build();

        // when
        Page<Game> getGames = gameService.getGames(request);

        assertEquals(2, getGames.getContent().size());
    }

    @Test
    @DisplayName("게임 리스트 0건 테스트")
    void testEmptyGetGames() {
        // given
        GetGamesRequest request = GetGamesRequest.builder()
                .page(0)
                .size(2)
                .build();

        // when
        Page<Game> games = gameService.getGames(request);

        assertEquals(0, games.getContent().size());
    }

    @Test
    @DisplayName("게임 ID로 조회")
    @Transactional
    void getGetGameById() {
        // given
        Game game1 = Game.builder()
                .title("Game 1")
                .producer("Producer 1")
                .logo("Game 1.png")
                .build();

        Game game2 = Game.builder()
                .title("Game 2")
                .producer("Producer 2")
                .logo("Game 2.png")
                .build();

        Game saveGame1 = gameRepository.save(game1);
        Game saveGame2 = gameRepository.save(game2);

        // when
        gameService.getGameById(saveGame1.getId());

        // then
        assertEquals(game1.getId(), saveGame1.getId());
        assertEquals(game1.getTitle(), saveGame1.getTitle());
        assertEquals(game2.getId(), saveGame2.getId());
        assertEquals(game2.getTitle(), saveGame2.getTitle());
    }

    @Test
    @DisplayName("게임 타이틀 조회")
    @Transactional
    void getGamesBySearch() {
        // given
        Game game1 = Game.builder()
                .title("리그오브레전드")
                .producer("라이엇 게임즈")
                .logo("리그오브레전드.png")
                .build();

        Game game2 = Game.builder()
                .title("스타크래프트 리마스터")
                .producer("블리자드")
                .logo("스타크래프트 리마스터.png")
                .build();

        Game game3 = Game.builder()
                .title("스타크래프트2")
                .producer("블리자드")
                .logo("스타크래프트2.png")
                .build();

        gameRepository.save(game1);
        gameRepository.save(game2);
        gameRepository.save(game3);

        // when
        GameSearchRequest request = GameSearchRequest.builder()
                .title("스타")
                .page(0)
                .size(2)
                .build();

        Page<Game> games = gameService.getGamesBySearch(request);

        // then
        assertEquals(2, games.getContent().size());
    }

    @Test
    @DisplayName("게임 정보 수정 테스트")
    @Transactional
    void modifyGame() {
        // given
        Game game = Game.builder()
                .title("Test Game")
                .producer("Test Producer")
                .logo("Test.png")
                .build();

        gameRepository.save(game);

        // when
        ModifyGameRequest request = ModifyGameRequest.builder()
                .title("Change Game")
                .logo("Change.png")
                .build();

        Game modifiedGame = gameService.modifyGame(game.getId(), request);

        // then
        assertNotNull(modifiedGame);
        assertEquals("Change Game", modifiedGame.getTitle());
        assertEquals("Change.png", modifiedGame.getLogo());
        assertEquals("Test Producer", modifiedGame.getProducer());
    }

    @Test
    @DisplayName("게임 정보 삭제 테스트")
    @Transactional
    void removeGame() {
        // given
        Game game = Game.builder()
                .title("Test Game")
                .producer("Test Producer")
                .logo("Test.png")
                .build();

        gameRepository.save(game);

        // when
        gameService.removeGame(game.getId());

        // then
        assertTrue(gameRepository.findById(game.getId()).isEmpty());
    }
}