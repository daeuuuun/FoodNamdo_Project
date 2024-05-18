package org.zerock.foodnamdo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.foodnamdo.baseDTO.ReviewDTO;
import org.zerock.foodnamdo.domain.ReviewEntity;

import java.util.List;

@Repository
public interface MyPageRepositoryReview extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findByUserEntity_UserId(Long userId);
}
