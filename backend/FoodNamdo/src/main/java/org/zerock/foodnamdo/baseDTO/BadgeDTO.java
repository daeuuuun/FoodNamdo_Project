package org.zerock.foodnamdo.baseDTO;


import lombok.*;
import org.zerock.foodnamdo.domain.BadgeEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BadgeDTO {

    private Long badge_id;
    private String badge_name;
    private String badge_comment;
    private String badge_unlock;

    public static BadgeDTO fromEntity(BadgeEntity entity) {
        return BadgeDTO.builder()
                .badge_id(entity.getBadgeId())
                .badge_name(entity.getBadgeName())
                .badge_comment(entity.getBadgeComment())
                .badge_unlock(entity.getBadgeUnlock())
                .build();
    }
}
