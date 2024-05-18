package org.zerock.foodnamdo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.zerock.foodnamdo.domain.FavoriteEntity;
import org.zerock.foodnamdo.domain.RstrEntity;
import org.zerock.foodnamdo.domain.UserEntity;
import org.zerock.foodnamdo.repository.search.UserSearch;

@Repository
public interface MyPageRepositoryFavorite extends JpaRepository<FavoriteEntity, Long>{
//    @Query("SELECT f.rstrEntity FROM FavoriteEntity f WHERE f.userEntity.userId = :userId")
//    Page<RstrEntity> findAllByUserEntity_UserId(Long userId, Pageable pageable);

    Page<FavoriteEntity> findAllByUserEntity_UserId(Long userId, Pageable pageable);
}
