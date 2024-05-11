package org.zerock.foodnamdo.dto;

import lombok.*;
import org.zerock.foodnamdo.domain.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO {

    private Long review_id;
    private Long rstr_id;
    private Long user_id;
    private String review_text;
    private LocalDateTime time_of_creation;
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

    public static ReviewDTO fromEntity(ReviewEntity entity) {
        return ReviewDTO.builder()
                .review_id(entity.getReviewId())
                .rstr_id(entity.getRstrEntity().getRstrId())
                .user_id(entity.getUserEntity().getUserId())
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
                .build();
    }

    public static List<ReviewDTO> fromEntities(List<ReviewEntity> entities) {
        return entities.stream()
                .map(ReviewDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
