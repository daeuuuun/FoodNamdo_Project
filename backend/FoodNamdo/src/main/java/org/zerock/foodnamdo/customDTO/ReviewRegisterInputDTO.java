package org.zerock.foodnamdo.customDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.zerock.foodnamdo.domain.ReviewEntity;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRegisterInputDTO {
    //    private Long review_id;
    private Long rstr_id;
//    private Long user_id;
    private String review_text;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime time_of_creation;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
//    private LocalDateTime time_of_revision;
//    private double rating;
    private double category_rating_taste;
    private double category_rating_price;
    private double category_rating_clean;
    private double category_rating_service;
    private double category_rating_amenities;
//    private boolean receipt;
//    private int like;
//    private int dislike;

    //생성자를 이용한 변환 메서드
    public ReviewRegisterDTO toReviewRegisterDTO(Long userId) {
        DecimalFormat df = new DecimalFormat("#.##");
        double rating = (category_rating_taste + category_rating_price + category_rating_clean
                + category_rating_service + category_rating_amenities) / 5;
        rating = Double.parseDouble(df.format(rating));

        return ReviewRegisterDTO.builder()
                .rstr_id(this.rstr_id)
                .user_id(userId)
                .review_text(this.review_text)
                .time_of_creation(this.time_of_creation)
                .time_of_revision(null)
                .category_rating_taste(this.category_rating_taste)
                .category_rating_price(this.category_rating_price)
                .category_rating_clean(this.category_rating_clean)
                .category_rating_service(this.category_rating_service)
                .category_rating_amenities(this.category_rating_amenities)
                .rating(rating)
                .receipt(false)
                .like(0)
                .dislike(0)
                .build();
    }
}
