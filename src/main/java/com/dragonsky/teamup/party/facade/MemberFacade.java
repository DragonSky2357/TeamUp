//package com.dragonsky.teamup.party.facade;
//
//import com.dragonsky.teamup.member.dto.request.ModifyMemberRequest;
//import com.dragonsky.teamup.member.dto.response.GetMemberResponse;
//import com.dragonsky.teamup.member.dto.response.ModifyMemberResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class PartyFacade {
//    private final PartyService partyService;
//
//    public GetMemberResponse getMember(Long id) {
//        return GetMemberResponse.of(partyService.getMember(id));
//    }
//
//    public ModifyMemberResponse modifyMember(Long id, ModifyMemberRequest request) {
//        return ModifyMemberResponse.of(partyService.modifyMember(id, request));
//    }
//
//    public void removeMember(Long id) {
//        partyService.removeMember(id);
//    }
//}
