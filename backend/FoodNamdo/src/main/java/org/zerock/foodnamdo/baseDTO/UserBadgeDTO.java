package org.zerock.foodnamdo.baseDTO;


import com.fasterxml.jackson.annotation.JsonFormat;
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
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
//    private LocalDateTime badge_date;
    private Boolean badge_on_off;
//    private int badge_sequence;

    public static UserBadgeDTO fromEntity(UserBadgeEntity entity) {
        return UserBadgeDTO.builder()
                .user_badge_id(entity.getUserBadgeId())
                .user_id(entity.getUserEntity().getUserId())
                .badge_id(entity.getBadgeEntity().getBadgeId())
//                .badge_date(entity.getBadgeDate())
                .badge_on_off(entity.getBadgeOnOff())
//                .badge_sequence(entity.getBadgeSequence())
                .build();
    }
}
