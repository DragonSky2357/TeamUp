package com.dragonsky.teamup.member.facade;

import com.dragonsky.teamup.member.dto.request.ModifyMemberRequest;
import com.dragonsky.teamup.member.dto.response.GetMemberResponse;
import com.dragonsky.teamup.member.dto.response.ModifyMemberResponse;
import com.dragonsky.teamup.member.dto.response.RemoveMemberResponse;
import com.dragonsky.teamup.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberFacade {
    private final MemberService memberService;

    public GetMemberResponse getMember(Long id) {
        return GetMemberResponse.of(memberService.getMember(id));
    }

    public ModifyMemberResponse modifyMember(Long id, ModifyMemberRequest request) {
        return ModifyMemberResponse.of(memberService.modifyMember(id,request));
    }

    public void removeMember(Long id) {
        memberService.removeMember(id);
    }
}
