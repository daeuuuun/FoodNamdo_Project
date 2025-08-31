package org.zerock.foodnamdo.baseDTO;


import lombok.*;
import org.zerock.foodnamdo.domain.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {
    private Long category_id;
    private String category_name;

    // 생성자를 이용한 변환 메서드
    public static CategoryDTO fromEntity(CategoryEntity entity) {
        return CategoryDTO.builder()
                .category_id(entity.getCategoryId())
                .category_name(entity.getCategoryName())
                .build();
    }

    public static List<CategoryDTO> fromEntities(List<CategoryEntity> entities) {
        return entities.stream()
                .map(CategoryDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public static String categoryNamesFromEntities(List<CategoryEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return "";
        }

        return entities.stream()
                .map(CategoryEntity::getCategoryName)
                .collect(Collectors.joining(", "));
    }
}
