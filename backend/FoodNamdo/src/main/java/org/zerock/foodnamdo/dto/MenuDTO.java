package org.zerock.foodnamdo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {
    private Long menuDescriptionId;
    private Long rstrId;
    private int menuId;
    private String menuCategorySub;
    private String menuName;
    private int menuPrice;
}
