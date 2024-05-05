package org.zerock.foodnamdo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.foodnamdo.domain.RstrEntity;
import org.zerock.foodnamdo.domain.UserEntity;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RstrDTO {
    private Long rstrId;
//    private String rstrImgId;
//    private String rstrImgUrl;
//    private String table;
//    private double distance;
    private int rstrNum;
    private String rstrRegion;
    private String rstrPermission;
    private String rstrName;
    private String rstrAddress;
    private double rstrLa;
    private double rstrLo;
    private String rstrTel;
    private String rstrIntro;
    private double rstrNaverRating;
    private double rstrReviewRating;
    private int example;
    private int relax;
    private int rstrReviewCount;
    private int rstrFavoriteCount;
    private int rstrParking;
    private int rstrPlay;
    private int rstrPet;
    private String rstrClosed;
    private String rstrBusinessHour;
    private int rstrDelivery;
    private List<MenuDescriptionDTO> menuDescriptions;

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
