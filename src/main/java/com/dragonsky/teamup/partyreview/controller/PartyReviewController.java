package com.dragonsky.teamup.partyreview.controller;

import com.dragonsky.teamup.global.security.member.MemberDetails;
import com.dragonsky.teamup.partyreview.dto.request.AddPartyReviewRequest;
import com.dragonsky.teamup.partyreview.dto.request.GetPartyReviewsRequest;
import com.dragonsky.teamup.partyreview.dto.response.AddPartyReviewResponse;
import com.dragonsky.teamup.partyreview.dto.response.GetPartyReviewResponse;
import com.dragonsky.teamup.partyreview.facade.PartyReviewFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/partys")
public class PartyReviewController {
    private final PartyReviewFacade partyReviewFacade;

    @PostMapping("/{partyId}/reviews")
    public ResponseEntity<AddPartyReviewResponse> addPartyReview(
            @RequestBody @Valid AddPartyReviewRequest request,
            @PathVariable Long partyId,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        return ResponseEntity.ok(partyReviewFacade.addPartyReview(request, partyId, memberDetails.getMember()));
    }

    @GetMapping("/{partyId}/reviews")
    public ResponseEntity<List<GetPartyReviewResponse>> getPartyReviews(
            @ModelAttribute GetPartyReviewsRequest request,
            @PathVariable Long partyId
    ) {
        return ResponseEntity.ok(partyReviewFacade.getPartyReviews(request, partyId));
    }

    @GetMapping("/{partyId}/reviews/{reviewId}")
    public ResponseEntity<GetPartyReviewResponse> getPartyReview(
            @PathVariable Long partyId,
            @PathVariable Long reviewId
    ) {
        return ResponseEntity.ok(partyReviewFacade.getPartyReview(partyId, reviewId));
    }
}
