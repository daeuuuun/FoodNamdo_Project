package org.zerock.foodnamdo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.foodnamdo.domain.BadgeEntity;
import org.zerock.foodnamdo.domain.UserBadgeEntity;

import java.util.Optional;

public interface MainSystemRepositoryUserBadge extends JpaRepository<UserBadgeEntity, Long> {
    Optional<UserBadgeEntity> findByUserEntity_UserIdAndBadgeEntity_BadgeId(Long userId, Long badgeId);
}
