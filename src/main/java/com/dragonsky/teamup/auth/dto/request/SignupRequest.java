package com.dragonsky.teamup.auth.dto.request;

import com.dragonsky.teamup.member.model.Member;
import com.dragonsky.teamup.member.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupRequest {
    @NotBlank(message="이메일은 필수 사항입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다")
    private String email;

    @NotBlank(message = "비밀번호는 필수 항목입니다")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,50}$", message = "비밀번호는 숫자 영문자를 포함한 8자에서 최대 50자입니다")
    private String password;

    @NotBlank(message = "닉네임은 필수 항목입니다")
    private String nickname;

    public Member toEntity(PasswordEncoder passwordEncoder){
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.MEMBER)
                .nickname(nickname)
                .build();
    }
}
