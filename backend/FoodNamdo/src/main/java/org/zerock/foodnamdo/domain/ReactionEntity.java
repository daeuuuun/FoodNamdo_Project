package org.zerock.foodnamdo.domain;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reaction")
public class ReactionEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "review_id")
    private ReviewEntity review;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "reaction_type", nullable = false)
    private String reactionType;
}
