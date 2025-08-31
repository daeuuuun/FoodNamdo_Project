package org.zerock.foodnamdo.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.foodnamdo.customDTO.*;
import org.zerock.foodnamdo.domain.ReviewEntity;
import org.zerock.foodnamdo.domain.RstrEntity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

public interface MainSystemService {

    Page<RstrEntity> findAllByOrderByRstrReviewRatingDesc(Pageable pageable);

    List<RstrEntity> findAllByOrderByRstrReviewRatingDesc();

    Page<RstrEntity> findAllByOrderByRstrReviewCountDesc(Pageable pageable);

    Page<RstrEntity> findAllByOrderByRstrFavoriteCountDesc(Pageable pageable);

    List<RstrEntity> findAllByOrderByRstrFavoriteCountDesc();

    Page<RstrEntity> findAllByRstrNameContainsAndFilters(String name, String category, String region, Pageable pageable);
//    Page<RstrEntity> findAllByRstrNameContains(String name, Pageable pageable);

    Page<ReviewEntity> findAllByRstrEntity_RstrId(Long rstrId, Pageable pageable);
//    Page<RstrEntity> findAllByRstrId(Long rstrId, Pageable pageable);

    RstrEntity findByRstrId(Long rstrId);

//    int countAllByRstrNameContainsAndFilters(String rstrName, String category, String location);
//    int countAllByRstrNameContains(String rstrName);

    long count();

    Long reviewBadgeUpdate(Long userId);

    Long reactionBadgeUpdate(Long userId);

    Long receiptBadgeUpdate(Long userId);

    Long allBadgeUpdate(Long userId);

    void postReviewImage(int reviewImgId) throws IOException;

    void deleteByReviewId(Long reviewID);

    void deleteReactionByReviewId(Long reviewID);

    Long saveReviewAndReturnId(ReviewRegisterDTO reviewRegisterDTO);

    void updateRestaurantRatingAndCount(Long rstrId);

    void updateRestaurantFavoriteCount(Long rstrId);

    void saveReviewImage(Long rstrId, MultipartFile image) throws IOException;

    void insertReviewReaction(ReactionReviewDTO reactionReviewDTO);

    void updateReviewReactions(ReviewEntity reviewEntity);

    boolean checkFavorite(Long rstrId, Long userId);

    boolean saveFavorite(RstrFavoriteRegisterDTO rstrFavoriteRegisterDTO);

    void updateReview(ReviewUpdateDTO reviewUpdateDTO, Long userId);

    void updateLastVisit(Long userId, Long rstrId);

//    OcrResponseDTO verifyreview(Long userId, Long reviewId, MultipartFile image) throws IOException, JSONException;

    String processOCR(MultipartFile file);

    Optional<ReviewEntity> findByReviewId(Long reviewId);

    String getRstrName_ReviewEntity(Long reviewId);

    void verifyReview(Long reviewId);

    Page<RstrEntity> findAll(Pageable pageable);

    JsonNode getRecommand(Long userId) throws IOException;

//    RstrEntity findByReviewEntity_reviewId(Long reviewId);

    ReviewEntity findRstrByReviewId(Long reviewId);

    void deleteReviewImg(Long reviewId);
//    ResponseEntity<String> processOCR(MultipartFile file);
}
