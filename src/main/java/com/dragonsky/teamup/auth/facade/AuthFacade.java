package com.dragonsky.teamup.auth.facade;

import com.dragonsky.teamup.auth.dto.request.SignupRequest;
import com.dragonsky.teamup.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthFacade {
    private final AuthService authService;

    public void signUp(SignupRequest request){
        this.authService.signUp(request);
    }
}
