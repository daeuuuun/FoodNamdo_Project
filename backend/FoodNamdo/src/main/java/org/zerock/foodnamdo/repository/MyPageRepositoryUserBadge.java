package org.zerock.foodnamdo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.foodnamdo.domain.UserBadgeEntity;
import org.zerock.foodnamdo.domain.UserEntity;

import java.util.List;

@Repository
public interface MyPageRepositoryUserBadge extends JpaRepository<UserBadgeEntity, Long> {
//    Page<FavoriteEntity> findAllByUserEntity_UserId(Long userId, Pageable pageable);

    List<UserBadgeEntity> findByUserEntity_UserId(Long userId);
}
