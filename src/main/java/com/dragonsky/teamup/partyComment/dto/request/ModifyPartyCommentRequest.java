package com.dragonsky.teamup.partyComment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ModifyPartyCommentRequest {
    @NotBlank(message = "파티 댓글은 필수 항목입니다")
    private String content;
}