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
    private String category_name;
    private Long rstr_id;
    private List<RstrImgDTO> rstr_images;
    private int rstr_num;
    private String rstr_region;
    private String rstr_permission;
    private String rstr_name;
    private String rstr_address;
    private Double rstr_la;
    private Double rstr_lo;
    private String rstr_tel;
    private String rstr_intro;
    private String rstr_naver_rating;
    private String rstr_review_rating;
//    private Double rstr_naver_rating;
//    private Double rstr_review_rating;
    private boolean example;
    private boolean relax;
    private int rstr_review_count;
    private int rstr_favorite_count;
    private boolean rstr_parking;
    private boolean rstr_play;
    private boolean rstr_pet;
    private String rstr_closed;
    private String rstr_business_hour;
    private boolean rstr_delivery;
//    private List<RstrCategoryDTO> rstr_categories;
    private List<ReviewDTO> reviews;
    private List<MenuDescriptionDTO> menu_descriptions;
//    private List<CategoryDTO> categories;
    private List<FavoriteDTO> favorites;

    // 생성자를 이용한 변환 메서드
    public static FindRstrByNameDTO fromEntity(RstrEntity entity) {

        String naverRating = entity.getRstrNaverRating() != null ? String.format("%.2f", entity.getRstrNaverRating()) : null;
        String reviewRating = entity.getRstrReviewRating() != null ? String.format("%.2f", entity.getRstrReviewRating()) : null;


        return FindRstrByNameDTO.builder()
                .category_name(CategoryDTO.categoryNamesFromEntities(entity.getCategories()))
                .rstr_id(entity.getRstrId())
                .rstr_images(RstrImgDTO.fromEntities(entity.getRstrImages()))
                .rstr_num(entity.getRstrNum())
                .rstr_region(entity.getRstrRegion())
                .rstr_permission(entity.getRstrPermission())
                .rstr_name(entity.getRstrName())
                .rstr_address(entity.getRstrAddress())
                .rstr_la(entity.getRstrLa())
                .rstr_lo(entity.getRstrLo())
                .rstr_tel(entity.getRstrTel())
                .rstr_intro(entity.getRstrIntro())
                .rstr_naver_rating(naverRating)
                .rstr_review_rating(reviewRating)
                .example(entity.isExample())
                .relax(entity.isRelax())
                .rstr_review_count(entity.getRstrReviewCount())
                .rstr_favorite_count(entity.getRstrFavoriteCount())
                .rstr_parking(entity.isRstrParking())
                .rstr_play(entity.isRstrPlay())
                .rstr_pet(entity.isRstrPet())
                .rstr_closed(entity.getRstrClosed())
                .rstr_business_hour(entity.getRstrBusinessHour())
                .rstr_delivery(entity.isRstrDelivery())
//                .rstr_categories(RstrCategoryDTO.fromEntities(entity.getRstrCategories()))
                .reviews(ReviewDTO.fromEntities(entity.getReviews()))
                .menu_descriptions(MenuDescriptionDTO.fromEntities(entity.getMenuDescriptions()))
//                .categories(CategoryDTO.fromEntities(entity.getCategories()))
                .favorites(FavoriteDTO.fromEntities(entity.getFavorites()))
                .build();
    }
}

