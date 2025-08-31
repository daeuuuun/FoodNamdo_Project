package org.zerock.foodnamdo.customDTO;

import lombok.*;
import org.zerock.foodnamdo.baseDTO.*;
import org.zerock.foodnamdo.domain.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindRstrByNameDTO {
    private Long rstr_id;
    private boolean example;
    private boolean relax;
    private String rstr_img_url;
    private String rstr_name;
    private String rstr_region;
    private String category_name;
    private String rstr_review_rating;
    private int rstr_review_count;
    private int rstr_favorite_count;

    // 생성자를 이용한 변환 메서드
    public static FindRstrByNameDTO fromEntity(RstrEntity entity) {

        String reviewRating = entity.getRstrReviewRating() != null ? String.format("%.2f", entity.getRstrReviewRating()) : null;


        return FindRstrByNameDTO.builder()
                .rstr_id(entity.getRstrId())
                .example(entity.isExample())
                .relax(entity.isRelax())
                .rstr_img_url(RstrImgDTO.ImgUrlsFromEntities(entity.getRstrImages()))
                .rstr_name(entity.getRstrName())
                .rstr_region(entity.getRstrRegion())
                .category_name(CategoryDTO.categoryNamesFromEntities(entity.getCategories()))
                .rstr_review_rating(reviewRating)
                .rstr_review_count(entity.getRstrReviewCount())
                .rstr_favorite_count(entity.getRstrFavoriteCount())
                .build();
    }
}

