package com.dragonsky.teamup.partyComment.service;

import com.dragonsky.teamup.member.model.Member;
import com.dragonsky.teamup.party.model.Party;
import com.dragonsky.teamup.partyComment.dto.request.AddPartyCommentRequest;
import com.dragonsky.teamup.partyComment.dto.request.GetPartyCommentsRequest;
import com.dragonsky.teamup.partyComment.dto.request.ModifyPartyCommentRequest;
import com.dragonsky.teamup.partyComment.dto.response.GetPartyCommentDto;
import com.dragonsky.teamup.partyComment.exception.PartyCommentErrorCode;
import com.dragonsky.teamup.partyComment.exception.PartyCommentException;
import com.dragonsky.teamup.partyComment.model.PartyComment;
import com.dragonsky.teamup.partyComment.repository.PartyCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PartyCommentService {
    private final PartyCommentRepository partyCommentRepository;

    @Transactional
    public PartyComment addPartyComment(AddPartyCommentRequest request, Party party, Member member) {
        PartyComment savePartyComment = partyCommentRepository.save(request.toEntity(party, member));
        log.info("파티 댓글 ID : {} 파티 ID : {} 파티 댓글 생성 완료", savePartyComment.getId(), party.getId());

        return savePartyComment;
    }

    public List<GetPartyCommentDto> getPartyComments(GetPartyCommentsRequest request, Long partyId) {
        return partyCommentRepository.findPartyComments(request, partyId);
    }

    public GetPartyCommentDto getPartyComment(Long partyId, Long commentId) {
        return partyCommentRepository.findPartyComment(partyId, commentId);
    }

    @Transactional
    public PartyComment modifyPartyComment(ModifyPartyCommentRequest request, Long partyId, Long commentId, Member member) {
        PartyComment partyComment = findPartyComment(partyId, commentId);

        Member partyCommentMember = partyComment.getMember();
        if (!Objects.equals(partyCommentMember.getId(), member.getId())) {
            throw new PartyCommentException(PartyCommentErrorCode.PARTY_COMMENT_MODIFY_NOT_PERMISSION);
        }

        PartyComment.modify(request, partyComment);

        PartyComment modifyPartyComment = partyCommentRepository.save(partyComment);
        log.info("파티 ID : {} 댓글 ID : {} 수정 완료", partyId, commentId);

        return modifyPartyComment;
    }

    @Transactional
    public void removePartyComment(Long partyId, Long commentId, Member member) {
        PartyComment partyComment = findPartyComment(partyId, commentId);

        Member partyCommentMember = partyComment.getMember();
        if (!Objects.equals(partyCommentMember.getId(), member.getId())) {
            throw new PartyCommentException(PartyCommentErrorCode.PARTY_COMMENT_DELETE_NOT_PERMISSION);
        }

        partyCommentRepository.delete(partyComment);
        log.info("파티 ID : {} 댓글 ID : {} 삭제 완료", partyId, commentId);
    }

    private PartyComment findPartyComment(Long partyId, Long commentId) {
        return partyCommentRepository.findByPartyIdAndId(partyId, commentId)
                .orElseThrow(() -> new PartyCommentException(PartyCommentErrorCode.PARTY_COMMENT_NOT_FOUND));
    }

}