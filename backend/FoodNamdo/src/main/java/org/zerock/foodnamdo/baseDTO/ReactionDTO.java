package org.zerock.foodnamdo.baseDTO;


import lombok.*;
import org.zerock.foodnamdo.domain.ReactionEntity;
import org.zerock.foodnamdo.domain.ReactionType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReactionDTO {

    private Long reaction_id;
    private Long review_id;
    private Long user_id;
    private ReactionType reaction_type;
//    private String reaction_type;

    public static ReactionDTO fromEntity(ReactionEntity entity) {
        return ReactionDTO.builder()
                .reaction_id(entity.getReactionId())
                .review_id(entity.getReviewEntity().getReviewId())
                .user_id(entity.getUserEntity().getUserId())
                .reaction_type(entity.getReactionType())
                .build();
    }
}
