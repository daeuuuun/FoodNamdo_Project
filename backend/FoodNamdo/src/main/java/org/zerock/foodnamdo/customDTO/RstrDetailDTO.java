package org.zerock.foodnamdo.customDTO;

import lombok.*;
import org.zerock.foodnamdo.baseDTO.CategoryDTO;
import org.zerock.foodnamdo.baseDTO.MenuDescriptionDTO;
import org.zerock.foodnamdo.baseDTO.ReviewDTO;
import org.zerock.foodnamdo.baseDTO.RstrImgDTO;
import org.zerock.foodnamdo.domain.RstrEntity;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RstrDetailDTO {
    private String rstr_region;
    private String category_name;
    private List<RstrImgDTO> rstr_images;
    private boolean example;
    private boolean relax;
    private String rstr_name;
    private String rstr_review_rating;
//    private Double rstr_review_rating;
    private int rstr_review_count;
    private String rstr_naver_rating;
//    private Double rstr_naver_rating;
    private String rstr_address;
    private String rstr_tel;
    private int rstr_favorite_count;
    private String rstr_intro;
    private String rstr_business_hour;
    private String rstr_closed;
    private List<MenuDescriptionExceptRstrIdDTO> menuDescriptions;
    private boolean rstr_parking;
    private boolean rstr_play;
    private boolean rstr_pet;
    private boolean rstr_delivery;
    private Double rstr_la;
    private Double rstr_lo;
    private List<ReviewDTO> reviews;


//    private String rstr_permission;
//    private List<RstrCategoryDTO> rstrCategories;
//    private List<RstrImgDTO> rstrImages;
//    private List<ReviewDTO> reviews;
//    private List<CategoryDTO> categories;
//    private List<FavoriteDTO> favorites;

    public static RstrDetailDTO fromEntity(RstrEntity entity) {
        String reviewRating = entity.getRstrReviewRating() != null ? String.format("%.2f", entity.getRstrReviewRating()) : null;
        String naverRating = entity.getRstrNaverRating() != null ? String.format("%.2f", entity.getRstrNaverRating()) : null;
        return RstrDetailDTO.builder()
                .rstr_region(entity.getRstrRegion())
                .category_name(CategoryDTO.categoryNamesFromEntities(entity.getCategories()))
                .rstr_images(RstrImgDTO.fromEntities(entity.getRstrImages()))
                .example(entity.isExample())
                .relax(entity.isRelax())
                .rstr_name(entity.getRstrName())
                .rstr_review_rating(reviewRating)
                .rstr_review_count(entity.getRstrReviewCount())
                .rstr_naver_rating(naverRating)
                .rstr_address(entity.getRstrAddress())
                .rstr_tel(entity.getRstrTel())
                .rstr_favorite_count(entity.getRstrFavoriteCount())
                .rstr_intro(entity.getRstrIntro())
                .rstr_business_hour(entity.getRstrBusinessHour())
                .rstr_closed(entity.getRstrClosed())
                .menuDescriptions(MenuDescriptionExceptRstrIdDTO.fromEntities(entity.getMenuDescriptions()))
                .rstr_parking(entity.isRstrParking())
                .rstr_play(entity.isRstrPlay())
                .rstr_pet(entity.isRstrPet())
                .rstr_delivery(entity.isRstrDelivery())
                .rstr_la(entity.getRstrLa())
                .rstr_lo(entity.getRstrLo())
                .reviews(ReviewDTO.fromEntities(entity.getReviews()))




//                .rstr_id(entity.getRstrId())
//                .rstr_num(entity.getRstrNum())
//                .rstr_permission(entity.getRstrPermission())
//                .example(entity.isExample())
//                .relax(entity.isRelax())
                .build();
    }

    public static List<RstrDetailDTO> fromEntities(List<RstrEntity> entities) {
        return entities.stream()
                .map(RstrDetailDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
