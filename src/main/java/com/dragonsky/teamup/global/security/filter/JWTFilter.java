package com.dragonsky.teamup.global.security.filter;

import com.dragonsky.teamup.global.security.jwt.JWTUtil;
import com.dragonsky.teamup.global.security.login.CustomUserDetails;
import com.dragonsky.teamup.member.model.Member;
import com.dragonsky.teamup.member.model.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();

        String access = null;
        String refresh = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("access".equals(cookie.getName())) {
                    access = cookie.getValue();
                } else if ("refresh".equals(cookie.getName())) {
                    refresh = cookie.getValue();
                }
            }
        }

        if (access == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (jwtUtil.isExpired(access)) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtUtil.getUsername(access);
        String role = jwtUtil.getRole(access);

//        var a = Arrays.stream(Role.values()).filter(r ->
//            r.getKey().equals(role)
//        );

        Member member = Member.builder()
                .email(username)
                .password("password")
                .role(Role.valueOf(role))
                .nickname("nickname")
                .build();

        CustomUserDetails customUserDetails = new CustomUserDetails(member);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
