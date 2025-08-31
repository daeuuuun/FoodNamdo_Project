package org.zerock.foodnamdo.customDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.foodnamdo.domain.UserEntity;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {
    @NotEmpty(message = "name cannot be null")
    @Schema(description = "사용자 이름", nullable = false, example = "박종진")
    private String name;

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String phone;

    @NotEmpty
    private String accountId;

    @NotEmpty
    private String password;

    public static SignUpDTO fromEntity(UserEntity user) {
        return new SignUpDTO(
                user.getName(),
                user.getNickname(),
                user.getPhone(),
                user.getAccountId(),
                user.getPassword()
        );
    }

    // Getters and setters
}
