package org.zerock.foodnamdo.customDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChangePasswordDTO {
    @Schema(description = "previous Password", example = "previous_password")
    private String prevPassword;
    @Schema(description = "new Password", example = "new_password")
    private String newPassword;
}
