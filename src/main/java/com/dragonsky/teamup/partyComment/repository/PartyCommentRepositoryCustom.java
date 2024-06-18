package com.dragonsky.teamup.partyComment.repository;

import com.dragonsky.teamup.partyComment.dto.request.GetPartyCommentsRequest;
import com.dragonsky.teamup.partyComment.dto.response.GetPartyCommentDto;

import java.util.List;

public interface PartyCommentRepositoryCustom {
    List<GetPartyCommentDto> findPartyComments(GetPartyCommentsRequest request, Long partyId);

    GetPartyCommentDto findPartyComment(Long partyId, Long commentId);
}