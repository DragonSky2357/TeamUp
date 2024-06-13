package com.dragonsky.teamup.member.service;

import com.dragonsky.teamup.member.dto.request.ModifyMemberRequest;
import com.dragonsky.teamup.member.exception.MemberErrorCode;
import com.dragonsky.teamup.member.exception.MemberException;
import com.dragonsky.teamup.member.model.Member;
import com.dragonsky.teamup.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() ->
                        new MemberException(MemberErrorCode.MEMBER_NOT_FOUND)
                );
    }

    @Transactional
    public Member modifyMember(Long id, ModifyMemberRequest request) {
        Member member = this.getMember(id);

        Member.modify(member, request);

        log.info("id : {} 회원 정보 변경", member.getId());

        return memberRepository.save(member);
    }

    @Transactional
    public void removeMember(Long id) {
        Member member = this.getMember(id);

        log.info("id : {} 회원 삭제", member.getId());

        memberRepository.delete(member);
    }
}
