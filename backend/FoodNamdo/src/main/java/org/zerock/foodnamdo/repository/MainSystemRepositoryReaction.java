package org.zerock.foodnamdo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.zerock.foodnamdo.domain.*;

import java.util.List;
import java.util.Optional;
import java.util.Collection;

@Repository
public interface MainSystemRepositoryReaction extends JpaRepository<ReactionEntity, Long> {
    ReactionEntity findByReviewEntityAndUserEntity(ReviewEntity reviewEntity, UserEntity userEntity);
    long countByReviewEntityAndReactionType(ReviewEntity reviewEntity, ReactionType reactionType);

    List<ReactionEntity> findAllByUserEntity_UserId(Long userId);

    void deleteAllByReviewEntity_ReviewId(Long reviewId);

//    @Query("SELECT COUNT(r) FROM ReactionEntity r WHERE r.reviewEntity.reviewId = :reviewId AND r.reactionType = :reactionType")
//    long countByReviewIdAndReactionType(@Param("reviewId") Long reviewId, @Param("reactionType") ReactionType reactionType);
//    long countByReviewIdAndReactionType(@Param("reviewId") Long reviewId, @Param("reactionType") ReactionType reactionType);


//    int findAllByReviewEntity_ReviewIdAnd
//    UserEntity findByUserId(Long rstrId);
}
