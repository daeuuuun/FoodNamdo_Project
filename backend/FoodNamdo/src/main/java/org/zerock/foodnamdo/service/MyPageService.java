package org.zerock.foodnamdo.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.foodnamdo.domain.FavoriteEntity;
import org.zerock.foodnamdo.domain.RstrEntity;
import org.zerock.foodnamdo.domain.UserEntity;

public interface MyPageService {

//    Page<RstrEntity> findAllByUserEntity_UserId(Long userId, Pageable pageable);
    Page<FavoriteEntity> findAllByUserEntity_UserId(Long userId, Pageable pageable);
}
