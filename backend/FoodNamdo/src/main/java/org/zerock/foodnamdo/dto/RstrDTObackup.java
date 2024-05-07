package org.zerock.foodnamdo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RstrDTObackup {
    private String category_name;
    private Long rstr_id;
    private List<RstrImgDTO> rstr_img;
//    private Long rstr_img_id;
//    private String rstr_img_url;
//    private String table;
//    private double distance;
    private int rstr_num;
    private String rstr_region;
    private String rstr_permission;
    private String rstr_name;
    private String rstr_address;
    private double rstr_la;
    private double rstr_lo;
    private String rstr_tel;
    private String rstr_intro;
    private double rstr_naver_rating;
    private double rstr_review_rating;
    private int example;
    private int relax;
    private int rstr_review_count;
    private int rstr_favorite_count;
    private int rstr_parking;
    private int rstr_play;
    private int rstr_pet;
    private String rstr_closed;
    private String rstr_business_hour;
    private int rstr_delivery;
    private List<MenuDescriptionDTO> menu_description;

//    public static RstrDTO fromRstr(RstrEntity rstr) {
//        return new RstrDTO(
//                rstr.getRstrId(),
//                rstr.getRstrNum(),
//                rstr.getRstrRegion(),
//                rstr.getRstrPermission(),
//                rstr.getRstrName(),
//                rstr.getRstrAddress(),
//                rstr.getRstrLa(),
//                rstr.getRstrLo(),
//                rstr.getRstrTel(),
//                rstr.getRstrIntro(),
//                rstr.getRstrNaverRating(),
//                rstr.getRstrReviewRating(),
//                rstr.isExample() ? 1 : 0,
//                rstr.isRelax() ? 1 : 0,
//                rstr.getRstrFavoriteCount(),
//                rstr.isRstrParking() ? 1 : 0,
//                rstr.isRstrPlay() ? 1 : 0,
//                rstr.isRstrPet() ? 1 : 0,
//                rstr.getRstrClosed(),
//                rstr.getRstrBusinessHour(),
//                rstr.isRstrDelivery() ? 1 : 0
//        );
//    }
}
