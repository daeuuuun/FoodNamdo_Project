package org.zerock.foodnamdo.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.foodnamdo.baseDTO.ReviewDTO;
import org.zerock.foodnamdo.baseDTO.UserDTO;
import org.zerock.foodnamdo.domain.FavoriteEntity;
import org.zerock.foodnamdo.domain.ReviewEntity;
import org.zerock.foodnamdo.domain.RstrEntity;
import org.zerock.foodnamdo.domain.UserEntity;

import java.util.List;

public interface MyPageService {

//    Page<RstrEntity> findAllByUserEntity_UserId(Long userId, Pageable pageable);
    Page<FavoriteEntity> findAllByUserEntity_UserId(Long userId, Pageable pageable);

    void changeNickname(Long userId, String nickname);

    void changePassword(Long userId, String password);

    List<ReviewEntity> findReviewByUserId(Long userId);

    UserEntity findByUserId(Long userId);
}
