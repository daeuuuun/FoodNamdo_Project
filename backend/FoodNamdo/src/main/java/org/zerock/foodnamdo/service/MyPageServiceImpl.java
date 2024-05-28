package org.zerock.foodnamdo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.zerock.foodnamdo.baseDTO.ReviewDTO;
import org.zerock.foodnamdo.baseDTO.UserDTO;
import org.zerock.foodnamdo.domain.*;
import org.zerock.foodnamdo.repository.*;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MyPageServiceImpl implements MyPageService{
    private final MyPageRepository myPageRepository;
    private final MyPageRepositoryFavorite myPageRepositoryFavorite;
    private final MyPageRepositoryReview myPageRepositoryReview;
    private final MyPageRepositoryUserBadge myPageRepositoryUserBadge;
    private final MyPageRepositoryBadge myPageRepositoryBadge;

//    public Page<RstrEntity> findAllByUserEntity_UserId(Long userId, Pageable pageable){
//        return myPageRepository.findByFavoriteEntities_UserEntity_UserId(userId, pageable);
//    }
    public Page<FavoriteEntity> findAllByUserEntity_UserId(Long userId, Pageable pageable){
        return myPageRepositoryFavorite.findAllByUserEntity_UserId(userId, pageable);
    }

    public void changeNickname(Long userId, String nickname) {
        UserEntity userEntity = myPageRepository.findByUserId(userId);
        userEntity.changeNickname(nickname);
        myPageRepository.save(userEntity);
    }

    public void changePassword(Long userId, String password) {
        UserEntity userEntity = myPageRepository.findByUserId(userId);
        userEntity.changePassword(password);
        myPageRepository.save(userEntity);
    }

    public UserEntity findByUserId(Long userId){
        return myPageRepository.findByUserId(userId);
    }

    public List<BadgeEntity> getAllBadges(){
        return myPageRepositoryBadge.findAll();
    }

    public List<ReviewEntity> findReviewByUserId(Long userId){
        return myPageRepositoryReview.findByUserEntity_UserId(userId);
    }

    public List<UserBadgeEntity> findUserBadgeByUserId(Long userId){
        return myPageRepositoryUserBadge.findByUserEntity_UserId(userId);
    }


}
