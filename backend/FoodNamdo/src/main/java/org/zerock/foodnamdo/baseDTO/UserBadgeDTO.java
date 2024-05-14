package org.zerock.foodnamdo.baseDTO;


import lombok.*;
import org.zerock.foodnamdo.domain.UserBadgeEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserBadgeDTO {

    private Long user_badge_id;
    private Long user_id;
    private Long badge_id;
    private LocalDateTime badge_date;

    public static UserBadgeDTO fromEntity(UserBadgeEntity entity) {
        return UserBadgeDTO.builder()
                .user_badge_id(entity.getUserBadgeId())
                .user_id(entity.getUserEntity().getUserId())
                .badge_id(entity.getBadgeEntity().getBadgeId())
                .badge_date(entity.getBadgeDate())
                .build();
    }
}
