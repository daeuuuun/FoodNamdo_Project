package org.zerock.foodnamdo.baseDTO;


import lombok.*;
import org.zerock.foodnamdo.domain.ReactionEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReactionDTO {

    private Long review_id;
    private Long user_id;
    private String reaction_type;

    public static ReactionDTO fromEntity(ReactionEntity entity) {
        return ReactionDTO.builder()
                .review_id(entity.getReviewEntity().getReviewId())
                .user_id(entity.getUserEntity().getUserId())
                .reaction_type(entity.getReactionType())
                .build();
    }
}
