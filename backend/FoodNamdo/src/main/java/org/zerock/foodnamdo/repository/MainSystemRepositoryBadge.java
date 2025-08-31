package org.zerock.foodnamdo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.foodnamdo.domain.BadgeEntity;
import org.zerock.foodnamdo.domain.ReviewImgEntity;

public interface MainSystemRepositoryBadge extends JpaRepository<BadgeEntity, Long> {
}
