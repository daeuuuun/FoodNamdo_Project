package org.zerock.foodnamdo.customDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.foodnamdo.baseDTO.*;
import org.zerock.foodnamdo.domain.UserEntity;

import java.util.List;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyPageDTO {

    private UserDTO user;

    private List<RstrDTO> rstrs;

    private List<BadgeDTO> badges;

    private List<UserBadgeDTO> userBadges;

    public static MyPageDTO fromEntity(UserEntity entity) {
        return MyPageDTO.builder()
                .user(UserDTO.fromEntity(entity))
//                .rstrs(RstrDTO.fromEntities(entity.getFavorites()))
                .build();
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
