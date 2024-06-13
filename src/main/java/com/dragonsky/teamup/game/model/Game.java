package com.dragonsky.teamup.game.model;

import com.dragonsky.teamup.game.dto.request.ModifyGameRequest;
import com.dragonsky.teamup.global.entity.Timestamped;
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

    public static void modify(Game game, ModifyGameRequest request) {
        game.title = request.getTitle() != null ? request.getTitle() : game.title;
        game.producer = request.getProducer() != null ? request.getProducer() : game.producer;
        game.logo = request.getLogo() != null ? request.getLogo() : game.logo;
    }
}
