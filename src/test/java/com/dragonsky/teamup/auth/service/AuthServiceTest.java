package com.dragonsky.teamup.auth.service;

import com.dragonsky.teamup.auth.dto.request.SignupRequest;
import com.dragonsky.teamup.auth.exception.ErrorCode;
import com.dragonsky.teamup.auth.exception.MemberException;
import com.dragonsky.teamup.auth.model.Refresh;
import com.dragonsky.teamup.auth.repository.RefreshRepository;
import com.dragonsky.teamup.global.util.jwt.JWTUtil;
import com.dragonsky.teamup.member.model.Member;
import com.dragonsky.teamup.member.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private RefreshRepository refreshRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private AuthService authService;

    private SignupRequest signupRequest;

    @BeforeEach
    public void setUp(){
        signupRequest = SignupRequest.builder()
                .email("test@naver.com")
                .nickname("test")
                .password("password")
                .build();
    }

    @Test
    @DisplayName("회원가입 성공")
    @Transactional
    void signUp() {
        when(memberRepository.findByEmail(signupRequest.getEmail())).thenReturn(null);
        when(passwordEncoder.encode(signupRequest.getPassword())).thenReturn("encodedPassword");
        when(memberRepository.save(any(Member.class))).thenReturn(Member.builder().build());

        authService.signUp(signupRequest);

        verify(memberRepository,times(1)).findByEmail(signupRequest.getEmail());
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    @DisplayName("회원가입 이메일 중복")
    public void testSignUp_DuplicateEmail() {
        Member member = Member.builder().build();
        when(memberRepository.findByEmail(signupRequest.getEmail())).thenReturn(member);

        MemberException exception = assertThrows(MemberException.class,() -> authService.signUp(signupRequest));
        assertEquals(ErrorCode.DUPLICATED_MEMBER.message,exception.getMessage());
        verify(memberRepository, times(1)).findByEmail(signupRequest.getEmail());
        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    public void testReisusu_Success(){
        // Given
        Cookie[] cookies = {new Cookie("refresh", "validRefreshToken")};
        when(request.getCookies()).thenReturn(cookies);
        when(jwtUtil.isExpired("validRefreshToken")).thenReturn(false);
        when(jwtUtil.getCategory("validRefreshToken")).thenReturn("refresh");
        when(jwtUtil.getId("validRefreshToken")).thenReturn(1L);
        when(jwtUtil.getUsername("validRefreshToken")).thenReturn("testUser");
        when(jwtUtil.getRole("validRefreshToken")).thenReturn("ROLE_USER");
        when(jwtUtil.createJwt(anyString(), anyLong(), anyString(), anyString())).thenReturn("newAccessToken");
        when(jwtUtil.createRefresh(anyString())).thenReturn("newRefreshToken");

        // When
        authService.reissue(request, response);

        // Then
        verify(response, times(2)).addCookie(any(Cookie.class));
        verify(refreshRepository, times(1)).deleteByRefresh("validRefreshToken");
        verify(refreshRepository, times(1)).save(any(Refresh.class));
    }
}