package org.zerock.foodnamdo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.foodnamdo.customDTO.ReactionReviewDTO;
import org.zerock.foodnamdo.customDTO.ReviewRegisterDTO;
import org.zerock.foodnamdo.customDTO.ReviewUpdateDTO;
import org.zerock.foodnamdo.customDTO.RstrFavoriteRegisterDTO;
import org.zerock.foodnamdo.domain.*;
import org.zerock.foodnamdo.repository.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MainSystemServiceImpl implements MainSystemService{

    private final ModelMapper modelMapper;
    private final MainSystemRepositoryRstr mainSystemRepositoryRstr;
    private final MainSystemRepositoryReview mainSystemRepositoryReview;
    private final MainSystemRepositoryUser mainSystemRepositoryUser;
    private final MainSystemRepositoryFavorite mainSystemRepositoryFavirote;
    private final MainSystemRepositoryReviewImg mainSystemRepositoryReviewImg;
    private final MainSystemRepositoryReaction mainSystemRepositoryReaction;

    @Override
    public Page<RstrEntity> findAllByOrderByRstrReviewCountDesc(Pageable pageable) {
        return mainSystemRepositoryRstr.findAllByOrderByRstrReviewCountDesc(pageable);
    }

    @Override
    public Page<RstrEntity> findAllByOrderByRstrFavoriteCountDesc(Pageable pageable) {
        return mainSystemRepositoryRstr.findAllByOrderByRstrFavoriteCountDesc(pageable);
    }

    public Page<RstrEntity> findAllByRstrNameContainsAndFilters(String name, String category, String region, Pageable pageable) {
        return mainSystemRepositoryRstr.findByRstrNameContainsAndFilters(name, category, region, pageable);
        //        return mainSystemRepositoryRstr.findAllByRstrNameContains(name, pageable);
    }
    public Page<ReviewEntity> findAllByRstrEntity_RstrId(Long rstrId, Pageable pageable) {
//    public Page<ReviewEntity> findAllByRstrEntity(Long rstrId, Pageable pageable) {
        return mainSystemRepositoryReview.findAllByRstrEntity_RstrId(rstrId, pageable);
    }

    public RstrEntity findByRstrId(Long rstrId) {
        return mainSystemRepositoryRstr.findByRstrId(rstrId);
    }

    public void updateLastVisit(Long userId, Long rstrId){
        mainSystemRepositoryUser.updateLastVisit(userId, rstrId);
    }

    public long count() {
        long count = mainSystemRepositoryRstr.count();
        return count;
    }

    @Override
    public void insertReviewReaction(ReactionReviewDTO reactionReviewDTO){
        ReviewEntity reviewEntity = mainSystemRepositoryReview.findById(reactionReviewDTO.getReviewId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid review ID"));
        UserEntity userEntity = mainSystemRepositoryUser.findById(reactionReviewDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        ReactionEntity existingReaction = mainSystemRepositoryReaction.findByReviewEntityAndUserEntity(reviewEntity, userEntity);

        if (existingReaction != null) {
            if (existingReaction.getReactionType() == reactionReviewDTO.getReactionType()) {
                mainSystemRepositoryReaction.delete(existingReaction);
            } else {
                existingReaction.setReactionType(reactionReviewDTO.getReactionType());
                mainSystemRepositoryReaction.save(existingReaction);
            }
        } else {
            ReactionEntity reactionEntity = ReactionEntity.builder()
                    .reviewEntity(reviewEntity)
                    .userEntity(userEntity)
                    .reactionType(reactionReviewDTO.getReactionType())
                    .build();
            mainSystemRepositoryReaction.save(reactionEntity);
        }
//        ReactionEntity reactionEntity = ReactionEntity.builder()
//                .reviewEntity(reviewEntity)
//                .userEntity(userEntity)
//                .reactionType(reactionReviewDTO.getReactionType())
//                .build();
//
//        mainSystemRepositoryReaction.save(reactionEntity);
        updateReviewReactions(reviewEntity);
    }

//    @Override
    public void updateReviewReactions(ReviewEntity reviewEntity){

        long likeCount = mainSystemRepositoryReaction.countByReviewEntityAndReactionType(reviewEntity, ReactionType.LIKE);
        long dislikeCount = mainSystemRepositoryReaction.countByReviewEntityAndReactionType(reviewEntity, ReactionType.DISLIKE);

//        ReviewEntity reviewEntity = mainSystemRepositoryReview.findById(reviewId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid review ID"));

        reviewEntity.setLike((int) likeCount);
        reviewEntity.setDislike((int) dislikeCount);

        mainSystemRepositoryReview.save(reviewEntity);

    }
    @Override
    public Long saveReviewAndReturnId(ReviewRegisterDTO reviewRegisterDTO) {
//    public void saveReview(ReviewRegisterDTO reviewRegisterDTO){
        RstrEntity rstrEntity = mainSystemRepositoryRstr.findById(reviewRegisterDTO.getRstr_id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid restaurant ID"));
        UserEntity userEntity = mainSystemRepositoryUser.findById(reviewRegisterDTO.getUser_id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

//        ReviewEntity reviewEntity = reviewRegisterDTO.toEntity(rstrEntity, userEntity);

        ReviewEntity reviewEntity = ReviewEntity.builder()
                .rstrEntity(rstrEntity)
                .userEntity(userEntity)
                .reviewText(reviewRegisterDTO.getReview_text())
                .timeOfCreation(reviewRegisterDTO.getTime_of_creation())
                .timeOfRevision(null)
                .rating(reviewRegisterDTO.getRating())
                .categoryRatingTaste(reviewRegisterDTO.getCategory_rating_taste())
                .categoryRatingPrice(reviewRegisterDTO.getCategory_rating_price())
                .categoryRatingClean(reviewRegisterDTO.getCategory_rating_clean())
                .categoryRatingService(reviewRegisterDTO.getCategory_rating_service())
                .categoryRatingAmenities(reviewRegisterDTO.getCategory_rating_amenities())
                .receipt(reviewRegisterDTO.isReceipt())
                .like(reviewRegisterDTO.getLike())
                .dislike(reviewRegisterDTO.getDislike())
                .build();

        mainSystemRepositoryReview.save(reviewEntity);
        updateRestaurantRatingAndCount(reviewRegisterDTO.getRstr_id());
        return reviewEntity.getReviewId();

//        if (reviewRegisterDTO.getReviewImages() != null && !reviewRegisterDTO.getReviewImages().isEmpty()) {
//            for (String imageUrl : reviewRegisterDTO.getReviewImages()) {
//                ReviewImgEntity reviewImgEntity = ReviewImgEntity.builder()
//                        .reviewEntity(reviewEntity)
//                        .reviewImgUrl(imageUrl)
//                        .build();
//                mainSystemRepositoryReviewImg.save(reviewImgEntity);
//            }
//        }
    }

    @Override
    @Transactional
    public void updateReview(ReviewUpdateDTO reviewUpdateDTO, Long userId) {
        ReviewEntity reviewEntity = mainSystemRepositoryReview.findById(reviewUpdateDTO.getReview_id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid review ID"));

        if (!reviewEntity.getUserEntity().getUserId().equals(userId)) {
            throw new SecurityException("You are not authorized to update this review");
        }

        if (reviewUpdateDTO.getReview_text() != null) {
            reviewEntity.setReviewText(reviewUpdateDTO.getReview_text());
        }
        if (reviewUpdateDTO.getCategory_rating_taste() != null) {
            reviewEntity.setCategoryRatingTaste(reviewUpdateDTO.getCategory_rating_taste());
        }
        if (reviewUpdateDTO.getCategory_rating_price() != null) {
            reviewEntity.setCategoryRatingPrice(reviewUpdateDTO.getCategory_rating_price());
        }
        if (reviewUpdateDTO.getCategory_rating_clean() != null) {
            reviewEntity.setCategoryRatingClean(reviewUpdateDTO.getCategory_rating_clean());
        }
        if (reviewUpdateDTO.getCategory_rating_service() != null) {
            reviewEntity.setCategoryRatingService(reviewUpdateDTO.getCategory_rating_service());
        }
        if (reviewUpdateDTO.getCategory_rating_amenities() != null) {
            reviewEntity.setCategoryRatingAmenities(reviewUpdateDTO.getCategory_rating_amenities());
        }
        reviewEntity.setTimeOfRevision(reviewUpdateDTO.getTime_of_revision());

        double rating = (reviewUpdateDTO.getCategory_rating_taste() + reviewUpdateDTO.getCategory_rating_price() +
                reviewUpdateDTO.getCategory_rating_clean() + reviewUpdateDTO.getCategory_rating_service() +
                reviewUpdateDTO.getCategory_rating_amenities()) / 5;

        DecimalFormat df = new DecimalFormat("#.##");
        rating = Double.parseDouble(df.format(rating));

        reviewEntity.setRating(rating);

        mainSystemRepositoryReview.save(reviewEntity);
        updateRestaurantRatingAndCount(reviewEntity.getRstrEntity().getRstrId());
    }

    @Transactional
    public void updateRestaurantRatingAndCount(Long rstrId) {
        RstrEntity rstrEntity = mainSystemRepositoryRstr.findById(rstrId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid restaurant ID"));

        List<ReviewEntity> reviews = mainSystemRepositoryReview.findByRstrEntity(rstrEntity);
        int reviewCount = reviews.size();
        double totalRating = reviews.stream().mapToDouble(ReviewEntity::getRating).sum();
        double averageRating = reviewCount > 0 ? totalRating / reviewCount : 0;

        rstrEntity.setRstrReviewCount(reviewCount);
        rstrEntity.setRstrReviewRating(averageRating);

        mainSystemRepositoryRstr.save(rstrEntity);
    }
    @Transactional
    public void updateRestaurantFavoriteCount(Long rstrId) {
        RstrEntity rstrEntity = mainSystemRepositoryRstr.findById(rstrId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid restaurant ID"));

        List<FavoriteEntity> favoriteEntities = mainSystemRepositoryFavirote.findByRstrEntity(rstrEntity);
        int favoriteCount = favoriteEntities.size();

        rstrEntity.setRstrFavoriteCount(favoriteCount);

        mainSystemRepositoryRstr.save(rstrEntity);
    }


    private final String uploadUrl = "http://foodnamdo.iptime.org:8000/images/?path=review_img&api_key=d0f535568d149afec1933a8c37c765e0";

    @Override
    public void saveReviewImage(Long reviewId, MultipartFile image) throws IOException {
        ReviewEntity reviewEntity = mainSystemRepositoryReview.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid review ID"));

        String imageUrl;
        try {
            imageUrl = uploadImageToServer(image);
        } catch (IOException e) {
            // 로깅 등 추가적인 예외 처리
            throw new IOException("Image upload failed", e);
        }

        ReviewImgEntity reviewImgEntity = ReviewImgEntity.builder()
                .reviewEntity(reviewEntity)
                .reviewImgUrl(imageUrl)
                .build();
        mainSystemRepositoryReviewImg.save(reviewImgEntity);

    }

    private String uploadImageToServer(MultipartFile image) throws IOException {
        String boundary = "===" + System.currentTimeMillis() + "===";
        HttpURLConnection connection = (HttpURLConnection) new URL(uploadUrl).openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        String originalFilename = image.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + "-" + originalFilename;

        try (OutputStream outputStream = connection.getOutputStream();
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true)) {

            writer.append("--").append(boundary).append("\r\n");
            writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(originalFilename).append("\"\r\n");
            writer.append("Content-Type: ").append(image.getContentType()).append("\r\n");
            writer.append("\r\n").flush();

            InputStream inputStream = image.getInputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();

            writer.append("\r\n").flush();
            writer.append("--").append(boundary).append("--").append("\r\n").flush();
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return "http://foodnamdo.iptime.org:8000/images/?image_name=" + originalFilename + "&path=review_img";
        } else {
            throw new IOException("Failed to upload image. Server returned HTTP response code: " + responseCode);
        }
    }

    public void saveFavorite(RstrFavoriteRegisterDTO rstrFavoriteRegisterDTO) {
        UserEntity userEntity = mainSystemRepositoryUser.findById(rstrFavoriteRegisterDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        RstrEntity rstrEntity = mainSystemRepositoryRstr.findById(rstrFavoriteRegisterDTO.getRstrId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid restaurant ID"));

        FavoriteEntity favorite = FavoriteEntity.builder()
                .userEntity(userEntity)
                .rstrEntity(rstrEntity)
                .build();

        mainSystemRepositoryFavirote.save(favorite);
    }

    @Override
    public void deleteByReviewId(Long reviewId) {
        mainSystemRepositoryReview.deleteByReviewId(reviewId);
    }
}
