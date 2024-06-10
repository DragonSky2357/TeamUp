package com.dragonsky.teamup.member.controller;

import com.dragonsky.teamup.member.facade.MemberFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {
    private final MemberFacade memberFacade;

    @GetMapping("/")
    public ResponseEntity<?> getMember(){
        return ResponseEntity.ok().build();
    }
}
