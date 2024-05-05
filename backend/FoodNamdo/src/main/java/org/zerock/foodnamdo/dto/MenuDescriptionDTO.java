package org.zerock.foodnamdo.dto;

import org.zerock.foodnamdo.domain.MenuDescriptionEntity;
import lombok.*;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDescriptionDTO {
    private Long menuDescriptionId;
    private Long rstrId;
    private int menuId;
    private String menuCategorySub;
    private String menuName;
    private int menuPrice;

//    public static MenuDescriptionDTO fromMenuDescriptionEntity(MenuDescriptionEntity menuDescriptionEntity) {
//        return MenuDescriptionDTO.builder()
//                .menuDescriptionId(menuDescriptionEntity.getMenuDescriptionId())
//                .rstrId(menuDescriptionEntity.getRestaurant().getRstrId())
//                .menuId(menuDescriptionEntity.getMenuId())
//                .menuCategorySub(menuDescriptionEntity.getMenuCategorySub())
//                .menuName(menuDescriptionEntity.getMenuName())
//                .menuPrice(menuDescriptionEntity.getMenuPrice())
//                .build();
//    }
}
