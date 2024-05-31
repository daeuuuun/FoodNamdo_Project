package org.zerock.foodnamdo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.foodnamdo.domain.ReviewEntity;
import org.zerock.foodnamdo.domain.RstrEntity;

import java.util.List;

@Repository
public interface MainSystemRepositoryReview extends JpaRepository<ReviewEntity, Long> {
//    Page<RstrEntity> findAllByOrderByRstrReviewCountDesc(Pageable pageable);

//    Page<RstrEntity> findAllByRstrNameContains(String name, Pageable pageable);

    List<ReviewEntity> findByRstrEntity(RstrEntity rstrEntity);
    Page<ReviewEntity> findAllByRstrEntity_RstrId(Long rstrId, Pageable pageable);

//    int countAllByRstrNameContains(String rstrName);

    void deleteByReviewId(Long reviewId);

    long count();

    ReviewEntity findByReviewId(Long reviewId);

    List<ReviewEntity> findAllByUserEntity_UserId(Long userId);
    List<ReviewEntity> findAllByUserEntity_UserIdAndReceiptTrue(Long userId);

}
