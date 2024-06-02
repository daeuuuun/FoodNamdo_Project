package org.zerock.foodnamdo.customDTO;

import lombok.*;
import org.zerock.foodnamdo.baseDTO.CategoryDTO;
import org.zerock.foodnamdo.baseDTO.RstrImgDTO;
import org.zerock.foodnamdo.domain.RstrEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindAllRstrDTO {
    private Long rstr_id;
    private boolean example;
    private boolean relax;
    private String rstr_img_url;
    private String rstr_name;
    private String rstr_region;
    private String category_name;
    private String rstr_review_rating;
    private int rstr_review_count;
    private int favorite_count;

    // 생성자를 이용한 변환 메서드
    public static FindAllRstrDTO fromEntity(RstrEntity entity) {

        String reviewRating = entity.getRstrReviewRating() != null ? String.format("%.2f", entity.getRstrReviewRating()) : null;


        return FindAllRstrDTO.builder()
                .rstr_id(entity.getRstrId())
                .example(entity.isExample())
                .relax(entity.isRelax())
                .rstr_img_url(RstrImgDTO.ImgUrlsFromEntities(entity.getRstrImages()))
                .rstr_name(entity.getRstrName())
                .rstr_region(entity.getRstrRegion())
                .category_name(CategoryDTO.categoryNamesFromEntities(entity.getCategories()))
                .rstr_review_rating(reviewRating)
                .rstr_review_count(entity.getRstrReviewCount())
                .favorite_count(entity.getRstrFavoriteCount())
                .build();
    }
}

