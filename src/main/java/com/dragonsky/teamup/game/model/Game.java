package com.dragonsky.teamup.game.model;

import com.dragonsky.teamup.global.entity.Timestamped;
import com.dragonsky.teamup.member.dto.request.ModifyMemberRequest;
import com.dragonsky.teamup.member.model.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE game SET deleted_at = CURRENT_TIMESTAMP where id = ?")
@Where(clause = "deleted_at IS NULL")
public class Game extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String producer;

    @Column(columnDefinition = "TEXT")
    private String logo;
}
