package org.zerock.foodnamdo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.foodnamdo.customDTO.ReviewRegisterDTO;
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

    void saveReview(ReviewRegisterDTO reviewRegisterDTO);


    String saveReviewImage(Long rstrId, MultipartFile image) throws IOException; // 수정된 부분

    void saveFavorite(RstrFavoriteRegisterDTO rstrFavoriteRegisterDTO);
}
