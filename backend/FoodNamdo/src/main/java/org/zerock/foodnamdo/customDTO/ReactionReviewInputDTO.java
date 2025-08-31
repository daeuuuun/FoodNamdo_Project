package org.zerock.foodnamdo.customDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.zerock.foodnamdo.domain.ReactionType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReactionReviewInputDTO {

    @Schema(description = "reviewID", example = "review_id")
    private Long reviewId;

    @Schema(description = "reactionType", example = "reaction_type")
    private ReactionType reactionType;
//    private String reactionType;
}

