package org.zerock.foodnamdo.dto;

import org.zerock.foodnamdo.domain.RstrCategoryEntity;

import lombok.*;
import org.zerock.foodnamdo.domain.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RstrCategoryDTO {
    private Long rstr_category_id;
    private RstrDTO restaurant;
    private CategoryDTO category;

    // 생성자를 이용한 변환 메서드
    public static RstrCategoryDTO fromEntity(RstrCategoryEntity entity) {
        return RstrCategoryDTO.builder()
                .rstr_category_id(entity.getRstrCategoryId())
                .restaurant(RstrDTO.fromEntity(entity.getRstrEntity()))
                .category(CategoryDTO.fromEntity(entity.getCategoryEntity()))
                .build();
    }

    public static List<RstrCategoryDTO> fromEntities(List<RstrCategoryEntity> entities) {
        return entities.stream()
                .map(RstrCategoryDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
