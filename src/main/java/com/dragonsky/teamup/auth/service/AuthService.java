package com.dragonsky.teamup.auth.service;

import com.dragonsky.teamup.auth.dto.request.SignupRequest;
import com.dragonsky.teamup.auth.exception.ErrorCode;
import com.dragonsky.teamup.auth.exception.MemberException;
import com.dragonsky.teamup.auth.model.Refresh;
import com.dragonsky.teamup.auth.repository.RefreshRepository;
import com.dragonsky.teamup.global.util.jwt.JWTUtil;
import com.dragonsky.teamup.member.model.Member;
import com.dragonsky.teamup.member.repository.MemberRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static com.dragonsky.teamup.global.util.cookie.CookieUtil.createCookie;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Transactional
    public void signUp(SignupRequest signupRequest) {
        Member findMember = memberRepository.findByEmail(signupRequest.getEmail());

        if (findMember != null) {
            log.warn("회원 가입 시도: 중복된 이메일 - {}", signupRequest.getEmail());
            throw new MemberException(ErrorCode.DUPLICATED_MEMBER, ErrorCode.DUPLICATED_MEMBER.message);
        }
        Member member = signupRequest.toEntity(passwordEncoder);
        memberRepository.save(member);

        log.info("{}님 회원 가입 성공 (이메일: {})", signupRequest.getNickname(), signupRequest.getEmail());
    }

    public void reissue(HttpServletRequest request, HttpServletResponse response) {
        String refresh = null;
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {
            //response status code
            log.info("refresh token null", HttpStatus.BAD_REQUEST);
        }

        //expired check
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {

            //response status code
            log.info("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {

            //response status code
            log.info("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        Long id = jwtUtil.getId(refresh);
        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        String newAccess = jwtUtil.createJwt("access", id, username, role);
        String newRefresh = jwtUtil.createRefresh("refresh");

        refreshRepository.deleteByRefresh(refresh);
        addRefresh(id, username, newRefresh, 86400000L);

        response.addCookie(createCookie("access", newAccess));
        response.addCookie(createCookie("refresh", newRefresh));
    }

    private void addRefresh(Long id, String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        Instant instant = date.toInstant();
        LocalDateTime expiration = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();

        Refresh refreshEntity = Refresh
                .builder()
                .id(id)
                .email(username)
                .refresh(refresh)
                .expiration(expiration)
                .build();

        refreshRepository.save(refreshEntity);
    }
}