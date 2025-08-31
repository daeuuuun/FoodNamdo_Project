package org.zerock.foodnamdo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.zerock.foodnamdo.domain.ReviewEntity;
import org.zerock.foodnamdo.domain.RstrEntity;

import java.util.List;

@Repository
public interface MainSystemRepositoryRstr extends JpaRepository<RstrEntity, Long> {
    Page<RstrEntity> findAllByOrderByRstrReviewCountDesc(Pageable pageable);

    Page<RstrEntity> findAllByOrderByRstrReviewRatingDesc(Pageable pageable);

    List<RstrEntity> findAllByOrderByRstrReviewRatingDesc();
//    Page<RstrEntity> findAllByRstrNameContains(String name, Pageable pageable);

    @Query("SELECT r FROM RstrEntity r " +
            "JOIN RstrCategoryEntity rc ON r.rstrId = rc.rstrEntity.rstrId " +
            "JOIN CategoryEntity c ON rc.categoryEntity.categoryId = c.categoryId " +
            "WHERE r.rstrName LIKE %:rstrName% " +
            "AND (:categoryName IS NULL OR c.categoryName = :categoryName) " +
            "AND (:region IS NULL OR r.rstrRegion = :region)")
    Page<RstrEntity> findByRstrNameContainsAndFilters(@Param("rstrName") String name,
                                                      @Param("categoryName") String category,
                                                      @Param("region") String region,
                                                      Pageable pageable);

    RstrEntity findByRstrId(Long RstrId);

//    Page<RstrEntity> findAllByRstrId(Long rstrId, Pageable pageable);

//    int countAllByRstrNameContains(String rstrName);

    long count();

    Page<RstrEntity> findAllByOrderByRstrFavoriteCountDesc(Pageable pageable);

    List<RstrEntity> findAllByOrderByRstrFavoriteCountDesc();

//    RstrEntity findByReviewEntity_reviewId(Long reviewId);
}
