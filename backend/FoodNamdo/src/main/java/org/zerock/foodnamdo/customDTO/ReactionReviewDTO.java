package org.zerock.foodnamdo.customDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.zerock.foodnamdo.domain.ReactionType;

//@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReactionReviewDTO {

    @Schema(description = "reviewID", example = "review_id")
    private Long reviewId;

    private Long userId;

    @Schema(description = "reactionType", example = "reaction_type")
    private ReactionType reactionType;
//    private String reactionType;
}

