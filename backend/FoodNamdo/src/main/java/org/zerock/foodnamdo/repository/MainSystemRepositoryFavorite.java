package org.zerock.foodnamdo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.foodnamdo.domain.FavoriteEntity;
import org.zerock.foodnamdo.domain.RstrEntity;
import org.zerock.foodnamdo.domain.UserEntity;

import java.util.List;

@Repository
public interface MainSystemRepositoryFavorite extends JpaRepository<FavoriteEntity, Long> {
    List<FavoriteEntity> findByRstrEntity(RstrEntity rstrEntity);


//    UserEntity findByUserId(Long rstrId);
}
