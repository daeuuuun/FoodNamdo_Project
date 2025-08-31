package org.zerock.foodnamdo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.foodnamdo.domain.ReviewImgEntity;
import org.zerock.foodnamdo.domain.RstrEntity;

public interface MainSystemRepositoryReviewImg extends JpaRepository<ReviewImgEntity, Long> {

    void deleteAllByReviewEntity_ReviewId(Long reviewId);
}
