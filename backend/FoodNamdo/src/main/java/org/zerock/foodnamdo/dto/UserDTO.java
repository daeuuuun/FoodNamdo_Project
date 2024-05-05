package org.zerock.foodnamdo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.foodnamdo.domain.UserEntity;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long userId;

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




    public static UserDTO fromUser(UserEntity user) {
        return new UserDTO(
                user.getUserId(),
                user.getName(),
                user.getNickname(),
                user.getPhone(),
                user.getAccountId(),
                user.getPassword()
        );
    }

    // Getters and setters
}
