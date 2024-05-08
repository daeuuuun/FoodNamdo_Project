package org.zerock.foodnamdo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
//    @JsonBackReference
    private ReviewEntity reviewEntity;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
//    @JsonBackReference
    private UserEntity userEntity;

    @Column(name = "reaction_type", nullable = false)
    private String reactionType;
}
