package org.zerock.foodnamdo.customDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.foodnamdo.baseDTO.RstrDTO;
import org.zerock.foodnamdo.domain.FavoriteEntity;

import java.util.Arrays;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteTestDTO {
    private RstrDTO rstr;
//    private List<RstrDTO> rstrs;
    public static FavoriteTestDTO fromEntity(FavoriteEntity entity) {
        return FavoriteTestDTO.builder()
                .rstr(RstrDTO.fromEntity(entity.getRstrEntity()))
                .build();
    }

    // Getters and setters
}

//        return FavoriteTestDTO.builder()
//                .user_id(entity.getUserId())
//                .name(entity.getName())
//                .nickname(entity.getNickname())
//                .phone(entity.getPhone())
//                .account_id(entity.getAccountId())
//                .password(entity.getPassword())
//                .favorites(FavoriteDTO.fromEntities(entity.getFavorites()))
////                .rstrs(RstrDTO.fromEntities(entity.getR))

//                .rstr_id(entity.getRstrEntity().getRstrId())
//                .rstr_num(entity.getRstrEntity().getRstrNum())
//                .rstr_region(entity.getRstrEntity().getRstrRegion())
//                .rstr_permission(entity.getRstrEntity().getRstrPermission())
//                .rstr_name(entity.getRstrEntity().getRstrName())
//                .rstr_address(entity.getRstrEntity().getRstrAddress())
//                .rstr_la(entity.getRstrEntity().getRstrLa())
//                .rstr_lo(entity.getRstrEntity().getRstrLo())
//                .rstr_tel(entity.getRstrEntity().getRstrTel())
//                .rstr_intro(entity.getRstrEntity().getRstrIntro())
//                .rstr_naver_rating(entity.getRstrEntity().getRstrNaverRating())
//                .rstr_review_rating(entity.getRstrEntity().getRstrReviewRating())
//                .example(entity.getRstrEntity().isExample())
//                .relax(entity.getRstrEntity().isRelax())
//                .rstr_review_count(entity.getRstrEntity().getRstrReviewCount())
//                .rstr_favorite_count(entity.getRstrEntity().getRstrFavoriteCount())
//                .rstr_parking(entity.getRstrEntity().isRstrParking())
//                .rstr_play(entity.getRstrEntity().isRstrPlay())
//                .rstr_pet(entity.getRstrEntity().isRstrPet())
//                .rstr_closed(entity.getRstrEntity().getRstrClosed())
//                .rstr_business_hour(entity.getRstrEntity().getRstrBusinessHour())
//                .rstr_delivery(entity.getRstrEntity().isRstrDelivery())
//                .build();
//                .build();


