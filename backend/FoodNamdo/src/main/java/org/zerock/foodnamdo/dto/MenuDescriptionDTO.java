package org.zerock.foodnamdo.dto;

import org.zerock.foodnamdo.domain.MenuDescriptionEntity;
import lombok.*;
import org.zerock.foodnamdo.domain.ReviewEntity;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDescriptionDTO {
    private Long menu_description_id;
    private Long rstr_id;
    private int menu_id;
    private String menu_category_sub;
    private String menu_name;
    private int menu_price;

    public static MenuDescriptionDTO fromEntity(MenuDescriptionEntity entity) {
        return MenuDescriptionDTO.builder()
                .menu_description_id(entity.getMenuDescriptionId())
                .rstr_id(entity.getRstrEntity().getRstrId())
                .menu_id(entity.getMenuId())
                .menu_category_sub(entity.getMenuCategorySub())
                .menu_name(entity.getMenuName())
                .menu_price(entity.getMenuPrice())
                .build();
    }

    public static List<MenuDescriptionDTO> fromEntities(List<MenuDescriptionEntity> entities) {
        return entities.stream()
                .map(MenuDescriptionDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
