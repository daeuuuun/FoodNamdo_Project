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
    private int pageSize;
    private int totalPages;
    private List<RstrDTO> rstr;
    private List<MenuDescriptionDTO> menu_description;
}
