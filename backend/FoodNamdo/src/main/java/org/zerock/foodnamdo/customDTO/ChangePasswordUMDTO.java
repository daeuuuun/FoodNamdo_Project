package org.zerock.foodnamdo.customDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChangePasswordUMDTO {
    @Schema(description = "UserId", example = "user_id")
    private Long userId;
    @Schema(description = "new Password", example = "new_password")
    private String newPassword;
}
