package org.zerock.foodnamdo.customDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class findAccountIdDTO {
    private String name;
    private String phone;
    private String code;
}
