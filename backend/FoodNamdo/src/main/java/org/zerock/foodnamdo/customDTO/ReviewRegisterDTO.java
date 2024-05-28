package org.zerock.foodnamdo.customDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.zerock.foodnamdo.baseDTO.CategoryDTO;
import org.zerock.foodnamdo.baseDTO.ReviewImgDTO;
import org.zerock.foodnamdo.baseDTO.RstrImgDTO;
import org.zerock.foodnamdo.domain.ReviewEntity;
import org.zerock.foodnamdo.domain.RstrEntity;
import org.zerock.foodnamdo.domain.UserEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRegisterDTO {
//    private Long review_id;
    private Long rstr_id;
    private Long user_id;
    private String review_text;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime time_of_creation;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
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
    private List<String> reviewImages;
}
