package org.zerock.foodnamdo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.foodnamdo.domain.RstrEntity;
import org.zerock.foodnamdo.domain.FavoriteEntity;
import org.zerock.foodnamdo.domain.UserEntity;
import org.zerock.foodnamdo.domain.UserPreferenceEntity;
import org.zerock.foodnamdo.repository.search.UserSearch;

import java.util.List;
import java.util.Optional;

@Repository
public interface MyPageRepository extends JpaRepository<UserEntity, Long> {
//    Page<FavoriteEntity> findAllByUserEntity_UserId(Long userId, Pageable pageable);

    UserEntity findByUserId(Long userId);


}
