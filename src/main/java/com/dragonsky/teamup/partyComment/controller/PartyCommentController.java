package com.dragonsky.teamup.partyComment.controller;

import com.dragonsky.teamup.global.security.member.MemberDetails;
import com.dragonsky.teamup.partyComment.dto.request.AddPartyCommentRequest;
import com.dragonsky.teamup.partyComment.dto.request.GetPartyCommentsRequest;
import com.dragonsky.teamup.partyComment.dto.request.ModifyPartyCommentRequest;
import com.dragonsky.teamup.partyComment.dto.response.AddPartyCommentResponse;
import com.dragonsky.teamup.partyComment.dto.response.GetPartyCommentResponse;
import com.dragonsky.teamup.partyComment.dto.response.GetPartyCommentsResponse;
import com.dragonsky.teamup.partyComment.dto.response.ModifyPartyCommentResponse;
import com.dragonsky.teamup.partyComment.facade.PartyCommentFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/partys")
public class PartyCommentController {
    private final PartyCommentFacade partyCommentFacade;

    @PostMapping("/{partyId}/comments")
    public ResponseEntity<AddPartyCommentResponse> addParty(
            @RequestBody @Valid AddPartyCommentRequest request,
            @PathVariable Long partyId,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        return ResponseEntity.ok(partyCommentFacade.addPartyComment(request, partyId, memberDetails.getMember()));
    }

    @GetMapping("/{partyId}/comments")
    public ResponseEntity<List<GetPartyCommentsResponse>> getPartys(
            @ModelAttribute GetPartyCommentsRequest request,
            @PathVariable Long partyId
    ) {
        return ResponseEntity.ok(partyCommentFacade.getPartyComments(request, partyId));
    }

    @GetMapping("/{partyId}/comments/{commentId}")
    public ResponseEntity<GetPartyCommentResponse> getParty(
            @PathVariable Long partyId,
            @PathVariable Long commentId
    ) {
        return ResponseEntity.ok(partyCommentFacade.getPartyComment(partyId, commentId));
    }

    @PutMapping("/{partyId}/comments/{commentId}")
    public ResponseEntity<ModifyPartyCommentResponse> modifyPartyComment(
            @RequestBody @Valid ModifyPartyCommentRequest request,
            @PathVariable Long partyId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal MemberDetails memberDetails

    ) {
        return ResponseEntity.ok(partyCommentFacade.modifyPartyComment(request, partyId, commentId, memberDetails.getMember()));
    }

    @DeleteMapping("/{partyId}/comments/{commentId}")
    public ResponseEntity<Void> removePartyComment(
            @PathVariable Long partyId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal MemberDetails memberDetails

    ) {
        partyCommentFacade.removePartyComment(partyId, commentId, memberDetails.getMember());
        return ResponseEntity.ok().build();
    }
}
