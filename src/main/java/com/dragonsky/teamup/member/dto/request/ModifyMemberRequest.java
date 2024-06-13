package com.dragonsky.teamup.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ModifyMemberRequest {
    @NotBlank
    private String nickname;
}
