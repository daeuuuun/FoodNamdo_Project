package org.zerock.foodnamdo.customDTO;

import lombok.*;
import org.zerock.foodnamdo.baseDTO.MenuDescriptionDTO;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RstrWithMenuDTO {
    private Long reviewId;
    private Long reviewImgId;
    private String reviewImgUrl;
    private Long rstrId;
    private String table;
    private double distance;
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
    private int rstrFavoriteCount;
    private int rstrParking;
    private int rstrPlay;
    private int rstrPet;
    private String rstrClosed;
    private String rstrBusinessHour;
    private int rstrDelivery;
    private List<MenuDescriptionDTO> menuDescription;

//    public static RstrWithMenuDTO fromRstrWithMenu(RstrEntity rstrEntity) {
//        RstrWithMenuDTO rstrDTO = new RstrWithMenuDTO();
//        rstrDTO.setRstrId(rstrEntity.getRstrId());
//
//        return rstrDTO;
//    }
}

