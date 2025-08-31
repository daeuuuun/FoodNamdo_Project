package org.zerock.foodnamdo.customDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class findPasswordDTO {
    private String accountId;
    private String name;
    private String phone;
    private String code;
}
