package org.zerock.foodnamdo.customDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessRefreshTokenRequestDTO {

    @Schema(description = "The access token", example = "your_access_token_here")
    private String accessToken;

    @Schema(description = "The refresh token", example = "your_refresh_token_here")
    private String refreshToken;
}
