package com.dragonsky.teamup.party.model;

import com.dragonsky.teamup.game.model.Game;
import com.dragonsky.teamup.global.entity.Timestamped;
import com.dragonsky.teamup.member.model.Member;
import com.dragonsky.teamup.party.dto.request.ModifyPartyRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE party SET deleted_at = CURRENT_TIMESTAMP where id = ?")
@Where(clause = "deleted_at IS NULL")
public class Party extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String content;

    @Column(nullable = false)
    private Integer people;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PartyStatus status;

    @Column(nullable = false)
    private Integer view;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public static void modify(ModifyPartyRequest request, Party party) {
        party.title = request.getTitle() != null ? request.getTitle() : party.title;
        party.content = request.getContent() != null ? request.getContent() : party.content;
        party.people = request.getPeople() != null ? request.getPeople() : party.people;
        party.status = request.getStatus() != null ? request.getStatus() : party.status;
        party.deadline = request.getDeadline() != null ? request.getDeadline() : party.deadline;
    }
}
