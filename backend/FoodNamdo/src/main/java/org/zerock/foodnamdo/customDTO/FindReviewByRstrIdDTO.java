package org.zerock.foodnamdo.customDTO;

import lombok.*;
import org.zerock.foodnamdo.baseDTO.ReviewDTO;
import org.zerock.foodnamdo.baseDTO.ReviewImgDTO;
import org.zerock.foodnamdo.domain.ReviewEntity;
import org.zerock.foodnamdo.domain.RstrEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindReviewByRstrIdDTO {
    private List<ReviewDTO> reviews;

    public static FindReviewByRstrIdDTO fromEntity(RstrEntity entity) {
        return FindReviewByRstrIdDTO.builder()
                .reviews(ReviewDTO.fromEntities(entity.getReviews()))
                .build();
    }

    public static List<FindReviewByRstrIdDTO> fromEntities(List<RstrEntity> entities) {
        return entities.stream()
                .map(FindReviewByRstrIdDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
