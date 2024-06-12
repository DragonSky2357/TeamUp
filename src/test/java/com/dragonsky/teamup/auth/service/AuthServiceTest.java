package com.dragonsky.teamup.auth.service;

import com.dragonsky.teamup.auth.dto.request.SignupRequest;
import com.dragonsky.teamup.auth.exception.MemberException;
import com.dragonsky.teamup.auth.repository.RefreshRepository;
import com.dragonsky.teamup.global.util.cookie.CookieUtil;
import com.dragonsky.teamup.global.util.jwt.JWTUtil;
import com.dragonsky.teamup.member.model.Member;
import com.dragonsky.teamup.member.model.Role;
import com.dragonsky.teamup.member.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JWTUtil jwtUtil;

    @MockBean
    private RefreshRepository refreshRepository;

    @MockBean
    private CookieUtil cookieUtil;

    @Test
    @DisplayName("회원가입 성공")
    void signUp() {
        // given
        SignupRequest signupRequest = SignupRequest.builder()
                .email("test@naver.com")
                .password("password")
                .nickname("nickname")
                .build();

        // when
        authService.signUp(signupRequest);

        // then
        Member member = memberRepository.findByEmail(signupRequest.getEmail());
        assertNotNull(member);
        assertEquals(signupRequest.getEmail(), member.getEmail());
    }

    @Test
    @DisplayName("중복된 이메일 회원 가입 요청")
    public void testSignUp_DuplicateEmail(){
        // given
        Member member = Member.builder()
                .email("test@naver.com")
                .nickname("nickname")
                .password("password")
                .role(Role.MEMBER)
                .build();
        memberRepository.save(member);

        SignupRequest signupRequest = SignupRequest.builder()
                .email("test@naver.com")
                .password("password")
                .nickname("nickname")
                .build();

        // when & then
        assertThrows(MemberException.class, ()->{
            authService.signUp(signupRequest);
        });
    }

    @Test
    public void testReissue(){
        // Given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Cookie refreshCookie  = new Cookie("refresh","testRefreshToken");
        Cookie[] cookies = {refreshCookie};

        when(request.getCookies()).thenReturn(cookies);
        when(jwtUtil.isExpired(anyString())).thenReturn(false);
        when(jwtUtil.getCategory(anyString())).thenReturn("refresh");
        when(jwtUtil.getId(anyString())).thenReturn(1L);
        when(jwtUtil.getUsername(anyString())).thenReturn("testUser");
        when(jwtUtil.getRole(anyString())).thenReturn("ROLE_MEMBER");
        when(jwtUtil.createJwt(anyString(), anyLong(), anyString(), anyString())).thenReturn("newAccessToken");
        when(jwtUtil.createRefresh(anyString())).thenReturn("newRefreshToken");

        // When
        authService.reissue(request, response);

        // Then
        verify(refreshRepository, times(1)).deleteByRefresh(anyString());
        verify(response, times(2)).addCookie(any(Cookie.class));
    }
}