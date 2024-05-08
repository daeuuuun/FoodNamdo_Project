package org.zerock.foodnamdo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.foodnamdo.domain.MenuDescriptionEntity;
import org.zerock.foodnamdo.domain.RstrEntity;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RstrDTO {
    private Long rstr_id;
    private int rstr_num;
    private String rstr_region;
    private String rstr_permission;
    private String rstr_name;
    private String rstr_address;
    private Double rstr_la;
    private Double rstr_lo;
    private String rstr_tel;
    private String rstr_intro;
    private Double rstr_naver_rating;
    private Double rstr_review_rating;
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
    private List<RstrCategoryDTO> rstrCategories;
    private List<RstrImgDTO> rstrImages;
    private List<ReviewDTO> reviews;
    private List<MenuDescriptionDTO> menuDescriptions;
    private List<CategoryDTO> categories;
    private List<FavoriteDTO> favorites;

    public static RstrDTO fromEntity(RstrEntity entity) {
        return RstrDTO.builder()
                .rstr_id(entity.getRstrId())
                .rstr_num(entity.getRstrNum())
                .rstr_region(entity.getRstrRegion())
                .rstr_permission(entity.getRstrPermission())
                .rstr_name(entity.getRstrName())
                .rstr_address(entity.getRstrAddress())
                .rstr_la(entity.getRstrLa())
                .rstr_lo(entity.getRstrLo())
                .rstr_tel(entity.getRstrTel())
                .rstr_intro(entity.getRstrIntro())
                .rstr_naver_rating(entity.getRstrNaverRating())
                .rstr_review_rating(entity.getRstrReviewRating())
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
                .build();
    }

    public static List<RstrDTO> fromEntities(List<RstrEntity> entities) {
        return entities.stream()
                .map(RstrDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
