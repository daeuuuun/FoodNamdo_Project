package org.zerock.foodnamdo.customDTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReviewUpdateDTO {
    private Long review_id;
    private String review_text;
    private Double category_rating_taste;
    private Double category_rating_price;
    private Double category_rating_clean;
    private Double category_rating_service;
    private Double category_rating_amenities;
//    private double categoryRatingTaste;
//    private double categoryRatingPrice;
//    private double categoryRatingClean;
//    private double categoryRatingService;
//    private double categoryRatingAmenities;
    private LocalDateTime time_of_revision;
}
