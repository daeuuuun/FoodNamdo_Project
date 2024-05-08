package org.zerock.foodnamdo.dto;
import lombok.*;
import org.zerock.foodnamdo.domain.RstrCategoryEntity;
import org.zerock.foodnamdo.domain.RstrImgEntity;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RstrImgDTO {
    private Long rstr_img_id;
    private Long rstr_id;
    private String rstr_img_url;

    // 생성자를 이용한 변환 메서드
    public static RstrImgDTO fromEntity(RstrImgEntity entity) {
        return RstrImgDTO.builder()
                .rstr_img_id(entity.getRstrImgId())
                .rstr_id(entity.getRstrEntity().getRstrId())
                .rstr_img_url(entity.getRstrImgUrl())
                .build();
    }

    public static List<RstrImgDTO> fromEntities(List<RstrImgEntity> entities) {
        return entities.stream()
                .map(RstrImgDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
