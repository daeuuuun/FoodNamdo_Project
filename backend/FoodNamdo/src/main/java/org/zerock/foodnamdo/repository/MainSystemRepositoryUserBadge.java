package org.zerock.foodnamdo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.foodnamdo.domain.BadgeEntity;
import org.zerock.foodnamdo.domain.UserBadgeEntity;

public interface MainSystemRepositoryUserBadge extends JpaRepository<UserBadgeEntity, Long> {
}
