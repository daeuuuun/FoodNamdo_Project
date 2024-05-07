package org.zerock.foodnamdo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RstrResponseDTO {
//    private int pageSize;
//    private int totalPages;
    private List<RstrDTObackup> rstr;
    private List<MenuDescriptionDTO> menu_description;

    public void setRstr(List<RstrDTObackup> rstr) {
        this.rstr = rstr;
    }

    public void setMenu_description(List<MenuDescriptionDTO> menu_description) {
        this.menu_description = menu_description;
    }
}
