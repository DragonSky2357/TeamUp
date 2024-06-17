package com.dragonsky.teamup.party.service;

import com.dragonsky.teamup.game.model.Game;
import com.dragonsky.teamup.game.repository.GameRepository;
import com.dragonsky.teamup.global.util.cookie.CookieUtil;
import com.dragonsky.teamup.global.util.jwt.JWTUtil;
import com.dragonsky.teamup.member.model.Member;
import com.dragonsky.teamup.member.model.Role;
import com.dragonsky.teamup.member.repository.MemberRepository;
import com.dragonsky.teamup.party.dto.request.AddPartyRequest;
import com.dragonsky.teamup.party.dto.request.GetPartyRequest;
import com.dragonsky.teamup.party.dto.request.ModifyPartyRequest;
import com.dragonsky.teamup.party.dto.response.GetPartyDto;
import com.dragonsky.teamup.party.exception.PartyException;
import com.dragonsky.teamup.party.model.Party;
import com.dragonsky.teamup.party.model.PartyStatus;
import com.dragonsky.teamup.party.repository.PartyRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class PartyServiceTest {
    @Autowired
    private PartyService partyService;

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;
    private Game game1;
    private Game game2;

    @MockBean
    private CookieUtil cookieUtil;

    @MockBean
    private JWTUtil jwtUtil;

    @BeforeEach
    public void setup() {
        member = Member.builder()
                .id(1L)
                .email("member1@email.com")
                .password("password")
                .nickname("member1")
                .role(Role.MEMBER)
                .build();
        member = memberRepository.save(member);

        game1 = Game.builder()
                .id(1L)
                .title("game1 title")
                .producer("game1 producer")
                .logo("game1 logo")
                .build();
        game2 = Game.builder()
                .id(2L)
                .title("game2 title")
                .producer("game2 producer")
                .logo("game2 logo")
                .build();
        gameRepository.saveAll(List.of(game1, game2));

        Party party1 = Party.builder()
                .id(1L)
                .title("party1 title")
                .content("party1 content")
                .people(5)
                .view(8)
                .deadline(LocalDateTime.now().plusDays(30))
                .status(PartyStatus.OPEN)
                .member(member)
                .game(game1)
                .build();
        Party party2 = Party.builder()
                .id(2L)
                .title("party2 title")
                .content("party2 content")
                .people(3)
                .view(2)
                .deadline(LocalDateTime.now().plusDays(15))
                .status(PartyStatus.OPEN)
                .member(member)
                .game(game1)
                .build();
        Party party3 = Party.builder()
                .id(3L)
                .title("party3 title")
                .content("party3 content")
                .people(2)
                .view(5)
                .deadline(LocalDateTime.now().plusDays(9))
                .status(PartyStatus.OPEN)
                .member(member)
                .game(game2)
                .build();
        partyRepository.saveAll(List.of(party1, party2, party3));
    }

    @Test
    @DisplayName("파티 등록 테스트")
    void testAddParty() {
        // given
        AddPartyRequest request = AddPartyRequest.builder()
                .title("Party Title Test")
                .content("Party Content Test")
                .people(5)
                .deadline(LocalDateTime.now().plusDays(10))
                .build();

        // when
        Party saveParty = partyService.addParty(request, member, game1);

        // then
        assertNotNull(saveParty);
        assertEquals(request.getTitle(), saveParty.getTitle());
        assertEquals(request.getContent(), saveParty.getContent());
    }

    @Test
    @DisplayName("파티 리스트 테스트")
    void testGetPartys() {
        // given
        GetPartyRequest request = GetPartyRequest
                .builder()
                .page(0)
                .size(20)
                .build();

        // when
        List<GetPartyDto> partyList = partyService.getPartys(request);

        // then
        assertEquals(partyList.size(), 3);
    }

    @Test
    @DisplayName("파티 querydsl Id 조회 테스트")
    void testGetPartyById() {
        // given

        // when
        GetPartyDto party = partyService.getPartyById(1L);

        // then
        assertNotNull(party);
        assertNotNull(party.getParty());
        assertNotNull(party.getGame());
        assertNotNull(party.getMember());
        assertEquals(party.getParty().getId(), 1L);
    }

    @Test
    @DisplayName("파티 ID 조회 실패 테스트")
    void testFailedGetPartyById() {
        // given

        // when
        GetPartyDto party = partyService.getPartyById(999L);

        // then
        assertNull(party);
    }

    @Test
    @DisplayName("파티 수정 테스트")
    void testModifyParty() {
        // given
        Party party = Party
                .builder()
                .title("party title")
                .content("party content")
                .people(2)
                .view(0)
                .status(PartyStatus.OPEN)
                .deadline(LocalDateTime.now().plusDays(7))
                .game(game2)
                .member(member)
                .build();
        partyRepository.save(party);

        ModifyPartyRequest request = ModifyPartyRequest
                .builder()
                .title("change title")
                .content("change content")
                .people(3)
                .status(PartyStatus.CLOSE)
                .deadline(LocalDateTime.now().plusDays(2))
                .build();

        Party modifyParty = partyService.modifyParty(4L, request, member);
        assertEquals(modifyParty.getTitle(), "change title");
        assertEquals(modifyParty.getContent(), "change content");
        assertEquals(modifyParty.getPeople(), 3);
        assertEquals(modifyParty.getStatus(), PartyStatus.CLOSE);
    }

    @Test
    @DisplayName("파티 수정 권한 없음 테스트")
    void testFailedNoPermissionModifyParty() {
        // given
        Party party = Party
                .builder()
                .title("party title")
                .content("party content")
                .people(2)
                .view(0)
                .status(PartyStatus.OPEN)
                .deadline(LocalDateTime.now().plusDays(7))
                .game(game2)
                .member(member)
                .build();
        Party saveParty = partyRepository.save(party);

        Member diffMeber = Member.builder()
                .id(2L)
                .email("diffmember@email.com")
                .password("password")
                .nickname("diffmember")
                .role(Role.MEMBER)
                .build();
        memberRepository.save(diffMeber);

        ModifyPartyRequest request = ModifyPartyRequest
                .builder()
                .title("change title")
                .content("change content")
                .people(3)
                .status(PartyStatus.CLOSE)
                .deadline(LocalDateTime.now().plusDays(2))
                .build();

        assertThrows(PartyException.class, () -> partyService.modifyParty(saveParty.getId(), request, diffMeber));
    }

    @Test
    @DisplayName("파티 삭제 테스트")
    @Transactional
    void testRemoveParty() {
        // given
        Party party = Party
                .builder()
                .title("party title")
                .content("party content")
                .people(2)
                .view(0)
                .status(PartyStatus.OPEN)
                .deadline(LocalDateTime.now().plusDays(7))
                .game(game2)
                .member(member)
                .build();
        Party saveParty = partyRepository.save(party);

        // when
        partyService.removeParty(saveParty.getId(), member);

        // then
        assertTrue(partyRepository.findById(saveParty.getId()).isEmpty());
    }

    @Test
    @DisplayName("파티 삭제 권한 없음 테스트")
    void testFailedNoPermissionRemoveParty() {
        // given
        Party party = Party
                .builder()
                .title("party title")
                .content("party content")
                .people(2)
                .view(0)
                .status(PartyStatus.OPEN)
                .deadline(LocalDateTime.now().plusDays(7))
                .game(game2)
                .member(member)
                .build();
        Party saveParty = partyRepository.save(party);

        Member diffMeber = Member.builder()
                .id(2L)
                .email("diffmember@email.com")
                .password("password")
                .nickname("diffmember")
                .role(Role.MEMBER)
                .build();
        memberRepository.save(diffMeber);

        assertThrows(PartyException.class, () -> partyService.removeParty(saveParty.getId(), diffMeber));
    }
}