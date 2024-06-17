package com.dragonsky.teamup.party.controller;

import com.dragonsky.teamup.global.security.member.MemberDetails;
import com.dragonsky.teamup.party.dto.request.AddPartyRequest;
import com.dragonsky.teamup.party.dto.request.GetPartyRequest;
import com.dragonsky.teamup.party.dto.request.GetPartyResponse;
import com.dragonsky.teamup.party.dto.request.ModifyPartyRequest;
import com.dragonsky.teamup.party.dto.response.AddPartyResponse;
import com.dragonsky.teamup.party.dto.response.GetPartysResponse;
import com.dragonsky.teamup.party.dto.response.ModifyPartyResponse;
import com.dragonsky.teamup.party.facade.PartyFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/partys")
public class PartyController {
    private final PartyFacade partyFacade;

    @PostMapping
    public ResponseEntity<AddPartyResponse> addParty(
            @RequestBody @Valid AddPartyRequest request,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        return ResponseEntity.ok(partyFacade.addParty(request, memberDetails.getMember()));
    }

    @GetMapping
    public ResponseEntity<List<GetPartysResponse>> getGames(
            @ModelAttribute GetPartyRequest request
    ) {
        return ResponseEntity.ok(partyFacade.getPartys(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPartyResponse> getPartyById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(partyFacade.getPartyById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModifyPartyResponse> modifyParty(
            @PathVariable Long id,
            @RequestBody @Valid ModifyPartyRequest request,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        return ResponseEntity.ok(partyFacade.modifyParty(id, request, memberDetails.getMember()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeParty(
            @PathVariable Long id,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        partyFacade.removeParty(id, memberDetails.getMember());
        return ResponseEntity.ok().build();
    }
}
