package org.zerock.foodnamdo.baseDTO;


import lombok.*;
import org.zerock.foodnamdo.domain.RstrEntity;
import org.zerock.foodnamdo.domain.UserPreferenceEntity;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPreferenceDTO {

    private Long preference_id;
    private Long user_id;
    private Long category_id;

    // 생성자를 이용한 변환 메서드
    public static UserPreferenceDTO fromEntity(UserPreferenceEntity entity) {
        return UserPreferenceDTO.builder()
                .preference_id(entity.getPreferenceId())
                .user_id(entity.getUserEntity().getUserId())
                .category_id(entity.getCategoryEntity().getCategoryId())
                .build();
    }

    public static List<UserPreferenceDTO> fromEntities(List<UserPreferenceEntity> entities) {
        return entities.stream()
                .map(UserPreferenceDTO::fromEntity)
                .collect(Collectors.toList());
    }

}
