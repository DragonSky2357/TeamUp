package com.dragonsky.teamup.member.service;

import com.dragonsky.teamup.auth.exception.MemberException;
import com.dragonsky.teamup.global.util.cookie.CookieUtil;
import com.dragonsky.teamup.global.util.jwt.JWTUtil;
import com.dragonsky.teamup.member.dto.request.ModifyMemberRequest;
import com.dragonsky.teamup.member.model.Member;
import com.dragonsky.teamup.member.model.Role;
import com.dragonsky.teamup.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @MockBean
    private JWTUtil jwtUtil;

    @MockBean
    private CookieUtil cookieUtil;

    @Test
    @DisplayName("멤버 조회")
    void getMember() {
        // given
        Member member = Member.builder()
                .email("test@naver.com")
                .password("password")
                .nickname("nickname")
                .role(Role.MEMBER)
                .build();

        memberRepository.save(member);

        // when
        memberService.getMember(member.getId());

        // then
        Member getMember = memberService.getMember(member.getId());
        assertNotNull(getMember);
        assertEquals(member.getId(), getMember.getId());
        assertEquals(member.getEmail(), getMember.getEmail());
    }

    @Test
    @DisplayName("존재 하지 않은 멤버 ID 조회")
    void nonexistentMember() {
        // given
        Member member = Member.builder()
                .email("test@naver.com")
                .password("password")
                .nickname("nickname")
                .role(Role.MEMBER)
                .build();

        memberRepository.save(member);

        // when & then
        assertThrows(MemberException.class, () ->
                memberService.getMember(9999L));
    }

    @Test
    @DisplayName("멤버 정보 변경")
    void modifyMember() {
        // given
        Member member = Member.builder()
                .email("test@naver.com")
                .password("password")
                .nickname("nickname")
                .role(Role.MEMBER)
                .build();
        memberRepository.save(member);

        ModifyMemberRequest modifyMemberRequest = ModifyMemberRequest.builder()
                .nickname("hello")
                .build();

        // when
        memberService.modifyMember(member.getId(), modifyMemberRequest);

        // then
        Member getMember = memberRepository.findById(member.getId()).get();
        assertNotNull(getMember);
        assertEquals("hello", getMember.getNickname());
    }

    @Test
    @DisplayName("멤버 삭제")
    void removeMember() {
        // given
        Member member = Member.builder()
                .email("test@naver.com")
                .password("password")
                .nickname("nickname")
                .role(Role.MEMBER)
                .build();
        memberRepository.save(member);

        // when
        memberService.removeMember(member.getId());

        // then
        assertFalse(memberRepository.existsById(member.getId()));
    }
}