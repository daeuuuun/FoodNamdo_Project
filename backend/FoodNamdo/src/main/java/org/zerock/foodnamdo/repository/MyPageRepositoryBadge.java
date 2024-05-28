package org.zerock.foodnamdo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.foodnamdo.domain.BadgeEntity;
import org.zerock.foodnamdo.domain.UserEntity;

@Repository
public interface MyPageRepositoryBadge extends JpaRepository<BadgeEntity, Long> {



}
