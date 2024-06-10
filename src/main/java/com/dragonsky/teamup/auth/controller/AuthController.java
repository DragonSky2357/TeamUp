package com.dragonsky.teamup.auth.controller;

import com.dragonsky.teamup.auth.dto.request.SignupRequest;
import com.dragonsky.teamup.auth.facade.AuthFacade;
import com.dragonsky.teamup.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthFacade authFacade;

    @PostMapping("/auth/signup")
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignupRequest request){
        this.authFacade.signUp(request);
        return ResponseEntity.ok().build();
    }

}
