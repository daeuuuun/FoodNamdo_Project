package org.zerock.foodnamdo.customDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.foodnamdo.baseDTO.CategoryDTO;
import org.zerock.foodnamdo.baseDTO.RstrImgDTO;
import org.zerock.foodnamdo.domain.FavoriteEntity;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class getFavoriteRstrDTO {
    private Long rstr_id;
    private boolean example;
    private boolean relax;
    private String rstr_img_url;
    private String rstr_name;
    private String rstr_region;
    private String category_name;
    private String rstr_review_rating;
    private int rstr_review_count;
    public static getFavoriteRstrDTO fromEntity(FavoriteEntity entity) {
        String rstrImgUrl = RstrImgDTO.ImgUrlsFromEntities(entity.getRstrEntity().getRstrImages());
        String reviewRating = entity.getRstrEntity().getRstrReviewRating() != null ? String.format("%.2f", entity.getRstrEntity().getRstrReviewRating()) : null;

        return getFavoriteRstrDTO.builder()
                .rstr_id(entity.getRstrEntity().getRstrId())
                .example(entity.getRstrEntity().isExample())
                .relax(entity.getRstrEntity().isRelax())
                .rstr_img_url(RstrImgDTO.ImgUrlsFromEntities(entity.getRstrEntity().getRstrImages()))
                .rstr_name(entity.getRstrEntity().getRstrName())
                .rstr_region(entity.getRstrEntity().getRstrRegion())
                .category_name(CategoryDTO.categoryNamesFromEntities(entity.getRstrEntity().getCategories()))
                .rstr_review_rating(reviewRating)
                .rstr_review_count(entity.getRstrEntity().getRstrReviewCount())

                .build();
    }

    // Getters and setters
}



