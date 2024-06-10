package com.dragonsky.teamup.auth.service;

import com.dragonsky.teamup.auth.dto.request.SignupRequest;
import com.dragonsky.teamup.auth.exception.ErrorCode;
import com.dragonsky.teamup.auth.exception.MemberException;
import com.dragonsky.teamup.member.model.Member;
import com.dragonsky.teamup.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService{
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(SignupRequest signupRequest) {
        Member findMember = memberRepository.findByEmail(signupRequest.getEmail());

        if(findMember != null){
            log.warn("회원 가입 시도: 중복된 이메일 - {}", signupRequest.getEmail());
            throw new MemberException(ErrorCode.DUPLICATED_MEMBER,ErrorCode.DUPLICATED_MEMBER.message);
        }
        Member member = signupRequest.toEntity(passwordEncoder);
        memberRepository.save(member);

        log.info("{}님 회원 가입 성공 (이메일: {})", signupRequest.getNickname(), signupRequest.getEmail());
    }
}