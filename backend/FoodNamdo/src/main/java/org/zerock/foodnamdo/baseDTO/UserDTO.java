package org.zerock.foodnamdo.baseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.zerock.foodnamdo.customDTO.FindReviewByRstrIdDTO;
import org.zerock.foodnamdo.domain.RstrEntity;
import org.zerock.foodnamdo.domain.UserEntity;

import java.util.List;
import java.util.stream.Collectors;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;


@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long user_id;

    @NotEmpty(message = "name cannot be null")
    @Schema(description = "사용자 이름", nullable = false, example = "박종진")
    private String name;

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String phone;

    @NotEmpty
    private String account_id;

    @NotEmpty
    private String password;

    public static UserDTO fromEntity(UserEntity user) {
        return new UserDTO(
                user.getUserId(),
                user.getName(),
                user.getNickname(),
                user.getPhone(),
                user.getAccountId(),
                user.getPassword()
        );
    }

    public static List<UserDTO> fromEntities(List<UserEntity> entities) {
        return entities.stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }
//    public static UserDTO fromEntity(UserEntity entity) {
//        return UserDTO.builder()
//                .user_id(entity.getUserId())
//                .name(entity.getName())
//                .nickname(entity.getNickname())
//                .phone(entity.getPhone())
//                .account_id(entity.getAccountId())
//                .password(entity.getPassword())
//                .build();
//    }

    // Getters and setters
}
