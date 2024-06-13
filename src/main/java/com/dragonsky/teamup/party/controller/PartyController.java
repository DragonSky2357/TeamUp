//package com.dragonsky.teamup.party.controller;
//
//import com.dragonsky.teamup.global.security.member.MemberDetails;
//import com.dragonsky.teamup.member.dto.request.ModifyMemberRequest;
//import com.dragonsky.teamup.member.dto.response.GetMemberResponse;
//import com.dragonsky.teamup.member.dto.response.ModifyMemberResponse;
//import com.dragonsky.teamup.member.facade.MemberFacade;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/v1/partys")
//public class PartyController {
//    private final MemberFacade memberFacade;
//
//    @GetMapping("/{id}")
//    public ResponseEntity<GetMemberResponse> getMember(
//            @PathVariable Long id
//    ) {
//        return ResponseEntity.ok(memberFacade.getMember(id));
//    }
//
//    @PutMapping()
//    public ResponseEntity<ModifyMemberResponse> modifyMember(
//            @AuthenticationPrincipal MemberDetails memberDetails,
//            @Valid @RequestBody ModifyMemberRequest request
//    ) {
//        return ResponseEntity.ok(memberFacade.modifyMember(memberDetails.getId(), request));
//    }
//
//    @DeleteMapping()
//    public ResponseEntity<Void> removeMember(
//            @AuthenticationPrincipal MemberDetails memberDetails
//    ) {
//        return ResponseEntity.ok().build();
//    }
//}
