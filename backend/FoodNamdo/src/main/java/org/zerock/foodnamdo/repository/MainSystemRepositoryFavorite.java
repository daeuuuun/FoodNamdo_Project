package org.zerock.foodnamdo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.foodnamdo.domain.FavoriteEntity;
import org.zerock.foodnamdo.domain.UserEntity;

@Repository
public interface MainSystemRepositoryFavorite extends JpaRepository<FavoriteEntity, Long> {


//    UserEntity findByUserId(Long rstrId);
}
