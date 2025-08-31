package org.zerock.foodnamdo.customDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyDTO {
    @Schema(description = "user Name", example = "name")
    private String name;
    @Schema(description = "user PhoneNumber", example = "phone")
    private String phone;
}
