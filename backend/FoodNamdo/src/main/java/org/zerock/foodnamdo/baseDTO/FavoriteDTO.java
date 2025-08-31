package org.zerock.foodnamdo.baseDTO;


import lombok.*;
import org.zerock.foodnamdo.domain.FavoriteEntity;
import org.zerock.foodnamdo.domain.RstrEntity;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteDTO {

    private Long favorite_id;
    private Long user_id;
//    private List<RstrDTO> rstrs;
    private Long rstr_id;

    public static FavoriteDTO fromEntity(FavoriteEntity entity) {
        return FavoriteDTO.builder()
                .favorite_id(entity.getFavoriteId())
                .user_id(entity.getUserEntity().getUserId())
//                .rstrs(RstrDTO.fromEntities((List<RstrEntity>) entity.getRstrEntity()))
                .rstr_id(entity.getRstrEntity().getRstrId())
                .build();
    }

    public static List<FavoriteDTO> fromEntities(List<FavoriteEntity> entities) {
        return entities.stream()
                .map(FavoriteDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
