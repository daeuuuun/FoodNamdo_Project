package org.zerock.foodnamdo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.foodnamdo.customDTO.ReactionReviewDTO;
import org.zerock.foodnamdo.customDTO.ReviewRegisterDTO;
import org.zerock.foodnamdo.customDTO.ReviewUpdateDTO;
import org.zerock.foodnamdo.customDTO.RstrFavoriteRegisterDTO;
import org.zerock.foodnamdo.domain.ReviewEntity;
import org.zerock.foodnamdo.domain.RstrEntity;

import java.io.IOException;
import java.util.List;

public interface MainSystemService {
    Page<RstrEntity> findAllByOrderByRstrReviewCountDesc(Pageable pageable);

    Page<RstrEntity> findAllByRstrNameContainsAndFilters(String name, String category, String region, Pageable pageable);
//    Page<RstrEntity> findAllByRstrNameContains(String name, Pageable pageable);

    Page<ReviewEntity> findAllByRstrEntity_RstrId(Long rstrId, Pageable pageable);
//    Page<RstrEntity> findAllByRstrId(Long rstrId, Pageable pageable);

    RstrEntity findByRstrId(Long rstrId);

//    int countAllByRstrNameContainsAndFilters(String rstrName, String category, String location);
//    int countAllByRstrNameContains(String rstrName);

    long count();

    void deleteByReviewId(Long reviewID);

    Long saveReviewAndReturnId(ReviewRegisterDTO reviewRegisterDTO);


    void saveReviewImage(Long rstrId, MultipartFile image) throws IOException;

    void insertReviewReaction(ReactionReviewDTO reactionReviewDTO);

    void updateReviewReactions(ReviewEntity reviewEntity);

    void saveFavorite(RstrFavoriteRegisterDTO rstrFavoriteRegisterDTO);

    void updateReview(ReviewUpdateDTO reviewUpdateDTO, Long userId);
}
