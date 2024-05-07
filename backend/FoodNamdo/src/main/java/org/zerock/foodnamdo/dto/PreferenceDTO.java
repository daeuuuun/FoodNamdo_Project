package org.zerock.foodnamdo.dto;


import lombok.*;
import org.zerock.foodnamdo.domain.CategoryEntity;
import org.zerock.foodnamdo.domain.PreferencesEntity;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PreferenceDTO {

    private Long preference_id;
    private Long user_id;

    // 생성자를 이용한 변환 메서드
    public static PreferenceDTO fromEntity(PreferencesEntity entity) {
        return PreferenceDTO.builder()
                .preference_id(entity.getPreferenceId())
                .user_id(entity.getUserEntity().getUserId())
                .build();
    }


}
