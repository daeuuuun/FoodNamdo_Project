package org.zerock.foodnamdo.customDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.foodnamdo.domain.MenuDescriptionEntity;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDescriptionExceptRstrIdDTO {
    private Long menu_description_id;
//    private Long rstr_id;
    private int menu_id;
    private String menu_category_sub;
    private String menu_name;
    private int menu_price;

    public static MenuDescriptionExceptRstrIdDTO fromEntity(MenuDescriptionEntity entity) {
        return MenuDescriptionExceptRstrIdDTO.builder()
                .menu_description_id(entity.getMenuDescriptionId())
//                .rstr_id(entity.getRstrEntity().getRstrId())
                .menu_id(entity.getMenuId())
                .menu_category_sub(entity.getMenuCategorySub())
                .menu_name(entity.getMenuName())
                .menu_price(entity.getMenuPrice())
                .build();
    }

//    public static MenuDescriptionDTO fromEntityExceptRstrId(MenuDescriptionEntity entity) {
//        return MenuDescriptionDTO.builder()
//                .menu_description_id(entity.getMenuDescriptionId())
//                .menu_id(entity.getMenuId())
//                .menu_category_sub(entity.getMenuCategorySub())
//                .menu_name(entity.getMenuName())
//                .menu_price(entity.getMenuPrice())
//                .build();
//    }

    public static List<MenuDescriptionExceptRstrIdDTO> fromEntities(List<MenuDescriptionEntity> entities) {
        return entities.stream()
                .map(MenuDescriptionExceptRstrIdDTO::fromEntity)
                .collect(Collectors.toList());
    }

//    public static List<MenuDescriptionDTO> fromEntitiesExceptRstrId(List<MenuDescriptionEntity> entities) {
//        return entities.stream()
//                .map(MenuDescriptionDTO::fromEntityExceptRstrId)
//                .collect(Collectors.toList());
//    }
}
