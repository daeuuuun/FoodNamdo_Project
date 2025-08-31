package org.zerock.foodnamdo.customDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.zerock.foodnamdo.baseDTO.ReviewImgDTO;
import org.zerock.foodnamdo.domain.ReviewEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserReviewDTO {
    private Long review_id;
    private Long rstr_id;
    private String rstrName;
    private Long user_id;
    private String userNickName;
    private String review_text;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime time_of_creation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime time_of_revision;
    private double rating;
    private double category_rating_taste;
    private double category_rating_price;
    private double category_rating_clean;
    private double category_rating_service;
    private double category_rating_amenities;
    private boolean receipt;
    private int like;
    private int dislike;
    private List<ReviewImgDTO> reviewImgs;

    public static UserReviewDTO fromEntity(ReviewEntity entity) {
        return UserReviewDTO.builder()
                .review_id(entity.getReviewId())
                .rstr_id(entity.getRstrEntity().getRstrId())
                .rstrName(entity.getRstrEntity().getRstrName())
                .user_id(entity.getUserEntity().getUserId())
                .userNickName(entity.getUserEntity().getNickname())
                .review_text(entity.getReviewText())
                .time_of_creation(entity.getTimeOfCreation())
                .time_of_revision(entity.getTimeOfRevision())
                .rating(entity.getRating())
                .category_rating_taste(entity.getCategoryRatingTaste())
                .category_rating_price(entity.getCategoryRatingPrice())
                .category_rating_clean(entity.getCategoryRatingClean())
                .category_rating_service(entity.getCategoryRatingService())
                .category_rating_amenities(entity.getCategoryRatingAmenities())
                .receipt(entity.isReceipt())
                .like(entity.getLike())
                .dislike(entity.getDislike())
                .reviewImgs(ReviewImgDTO.fromEntities(entity.getReviewImages()))
                .build();
    }

    public static List<UserReviewDTO> fromEntities(List<ReviewEntity> entities) {
        return entities.stream()
                .map(UserReviewDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
