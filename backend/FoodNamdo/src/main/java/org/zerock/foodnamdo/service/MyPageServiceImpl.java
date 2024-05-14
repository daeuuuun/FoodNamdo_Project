package org.zerock.foodnamdo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.foodnamdo.domain.FavoriteEntity;
import org.zerock.foodnamdo.domain.RstrEntity;
import org.zerock.foodnamdo.domain.UserEntity;
import org.zerock.foodnamdo.repository.MyPageRepository;
import org.zerock.foodnamdo.repository.MyPageRepositoryFavorite;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MyPageServiceImpl implements MyPageService{
    private final MyPageRepository myPageRepository;
    private final MyPageRepositoryFavorite myPageRepositoryFavorite;

//    public Page<RstrEntity> findAllByUserEntity_UserId(Long userId, Pageable pageable){
//        return myPageRepository.findByFavoriteEntities_UserEntity_UserId(userId, pageable);
//    }
    public Page<FavoriteEntity> findAllByUserEntity_UserId(Long userId, Pageable pageable){
        return myPageRepository.findAllByUserEntity_UserId(userId, pageable);
    }
}
