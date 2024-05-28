package org.zerock.foodnamdo.customDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChangeNicknameDTO {
    @Schema(description = "new Nickname", example = "nickname")
    private String nickname;
}
