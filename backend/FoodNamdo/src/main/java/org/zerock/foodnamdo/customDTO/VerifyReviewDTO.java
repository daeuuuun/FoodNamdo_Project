package org.zerock.foodnamdo.customDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VerifyReviewDTO {
    @Schema(description = "rstr_id", example = "rstr_id")
    private Long rstrId;
}
