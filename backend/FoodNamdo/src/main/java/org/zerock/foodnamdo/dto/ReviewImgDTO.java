package org.zerock.foodnamdo.dto;

import lombok.*;
import org.zerock.foodnamdo.domain.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewImgDTO {

    private Long review_img_id;
    private Long review_id;
    private String review_img_url;

    // 생성자를 이용한 변환 메서드
    public static ReviewImgDTO fromEntity(ReviewImgEntity entity) {
        return ReviewImgDTO.builder()
                .review_img_id(entity.getReviewImgId())
                .review_id(entity.getReviewEntity().getReviewId())
                .review_img_url(entity.getReviewImgUrl())
                .build();
    }


    public static List<ReviewImgDTO> fromEntities(List<ReviewImgEntity> entities) {
        return entities.stream()
                .map(ReviewImgDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
