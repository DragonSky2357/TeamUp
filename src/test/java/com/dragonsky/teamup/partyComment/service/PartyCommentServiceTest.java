package com.dragonsky.teamup.partyComment.service;

import com.dragonsky.teamup.game.model.Game;
import com.dragonsky.teamup.game.repository.GameRepository;
import com.dragonsky.teamup.global.util.cookie.CookieUtil;
import com.dragonsky.teamup.global.util.jwt.JWTUtil;
import com.dragonsky.teamup.member.model.Member;
import com.dragonsky.teamup.member.model.Role;
import com.dragonsky.teamup.member.repository.MemberRepository;
import com.dragonsky.teamup.party.model.Party;
import com.dragonsky.teamup.party.model.PartyStatus;
import com.dragonsky.teamup.party.repository.PartyRepository;
import com.dragonsky.teamup.partyComment.dto.request.AddPartyCommentRequest;
import com.dragonsky.teamup.partyComment.dto.request.GetPartyCommentsRequest;
import com.dragonsky.teamup.partyComment.dto.request.ModifyPartyCommentRequest;
import com.dragonsky.teamup.partyComment.dto.response.GetPartyCommentDto;
import com.dragonsky.teamup.partyComment.exception.PartyCommentException;
import com.dragonsky.teamup.partyComment.model.PartyComment;
import com.dragonsky.teamup.partyComment.repository.PartyCommentRepository;
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
class PartyCommentServiceTest {

    @Autowired
    private PartyCommentService partyCommentService;

    @Autowired
    private PartyCommentRepository partyCommentRepository;

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Party party;
    private Game game;
    private Member member;
    private PartyComment comment1;
    private PartyComment comment2;
    private PartyComment comment3;

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

        game = Game.builder()
                .id(1L)
                .title("game1 title")
                .producer("game1 producer")
                .logo("game1 logo")
                .build();
        game = gameRepository.save(game);

        party = Party.builder()
                .id(1L)
                .title("party1 title")
                .content("party1 content")
                .people(5)
                .view(8)
                .deadline(LocalDateTime.now().plusDays(30))
                .status(PartyStatus.OPEN)
                .member(member)
                .game(game)
                .build();
        party = partyRepository.save(party);

        comment1 = PartyComment.builder()
                .content("comment 1")
                .party(party)
                .member(member)
                .build();

        comment2 = PartyComment.builder()
                .content("comment 2")
                .party(party)
                .member(member)
                .build();

        comment3 = PartyComment.builder()
                .content("comment 3")
                .party(party)
                .member(member)
                .build();

        partyCommentRepository.saveAll(List.of(comment1, comment2, comment3));
    }

    @Test
    @DisplayName("파티 댓글 등록 테스트")
    void testAddPartyComment() {
        // given
        AddPartyCommentRequest request = AddPartyCommentRequest.builder()
                .content("Party Comment Test")
                .build();

        // when
        PartyComment partyComment = partyCommentService.addPartyComment(request, party, member);

        // then
        assertNotNull(partyComment);
        assertEquals(partyComment.getContent(), request.getContent());
    }

    @Test
    @DisplayName("파티 댓글 리스트 조회 테스트")
    void testGetPartyComments() {
        // given
        GetPartyCommentsRequest request = GetPartyCommentsRequest.builder()
                .page(0)
                .size(20)
                .build();

        // when
        List<GetPartyCommentDto> partyCommentDtoList = partyCommentService.getPartyComments(request, party.getId());

        // then
        assertEquals(partyCommentDtoList.size(), 3);
    }

    @Test
    @DisplayName("파티 댓글 조회 테스트")
    void testGetPartyComment() {
        // given

        // when
        GetPartyCommentDto partyCommentDto = partyCommentService.getPartyComment(party.getId(), comment1.getId());

        // then
        assertNotNull(partyCommentDto);
        assertNotNull(partyCommentDto.getMember());
    }

    @Test
    @DisplayName("파티 댓글 조회 테스트")
    void testFailedGetPartyComment() {
        // given

        // when
        GetPartyCommentDto partyCommentDto = partyCommentService.getPartyComment(party.getId(), 999L);

        // then
        assertNull(partyCommentDto);
    }

    @Test
    @DisplayName("파티 댓글 수정 테스트")
    void testModifyPartyComment() {
        // given
        PartyComment partyComment = PartyComment.builder()
                .content("party comment")
                .party(party)
                .member(member)
                .build();
        partyComment = partyCommentRepository.save(partyComment);

        ModifyPartyCommentRequest request = ModifyPartyCommentRequest.builder()
                .content("change comment")
                .build();

        // when
        PartyComment modifyPartyComment = partyCommentService.modifyPartyComment(
                request, party.getId(),
                partyComment.getId(),
                member
        );

        // then
        assertEquals(modifyPartyComment.getContent(), "change comment");
    }

    @Test
    @DisplayName("파티 댓글 수정 권한 없음 테스트")
    void testFailedNoPermissionModifyPartyComment() {
        // given
        Member diffMember = Member.builder()
                .email("member2@email.com")
                .password("password")
                .nickname("member2")
                .role(Role.MEMBER)
                .build();
        memberRepository.save(diffMember);

        PartyComment partyComment = PartyComment.builder()
                .content("party comment")
                .party(party)
                .member(member)
                .build();
        partyCommentRepository.save(partyComment);

        ModifyPartyCommentRequest request = ModifyPartyCommentRequest.builder()
                .content("change comment")
                .build();

        // when & then
        assertThrows(
                PartyCommentException.class,
                () -> partyCommentService.modifyPartyComment(
                        request,
                        party.getId(),
                        partyComment.getId(),
                        diffMember
                )
        );
    }

    @Test
    @DisplayName("파티 댓글 삭제 테스트")
    void testRemovePartyComment() {
        // given
        PartyComment partyComment = PartyComment.builder()
                .content("party comment")
                .party(party)
                .member(member)
                .build();
        partyCommentRepository.save(partyComment);

        // when
        partyCommentService.removePartyComment(party.getId(), partyComment.getId(), member);

        // then
        assertTrue(partyCommentRepository.findByPartyIdAndId(party.getId(), partyComment.getId()).isEmpty());
    }

    @Test
    @DisplayName("파티 댓글 삭제 권한 없음 테스트")
    void testFailedNoPermissionRemovePartyComment() {
        // given
        PartyComment partyComment = PartyComment.builder()
                .content("party comment")
                .party(party)
                .member(member)
                .build();
        partyCommentRepository.save(partyComment);

        Member diffMeber = Member.builder()
                .email("diffmember@email.com")
                .password("password")
                .nickname("diffmember")
                .role(Role.MEMBER)
                .build();
        memberRepository.save(diffMeber);

        // when & then
        assertThrows(PartyCommentException.class,
                () -> partyCommentService.removePartyComment(
                        party.getId(),
                        partyComment.getId(),
                        diffMeber
                )
        );
    }
}