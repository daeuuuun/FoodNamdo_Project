package org.zerock.foodnamdo.customDTO;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
public class RstrFavoriteRegisterDTO {
    private Long userId;
    private Long rstrId;
}
