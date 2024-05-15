package org.zerock.foodnamdo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.foodnamdo.domain.ReviewEntity;
import org.zerock.foodnamdo.domain.RstrEntity;

@Repository
public interface MainSystemRepositoryRstr extends JpaRepository<RstrEntity, Long> {
    Page<RstrEntity> findAllByOrderByRstrReviewCountDesc(Pageable pageable);
    Page<RstrEntity> findAllByRstrNameContains(String name, Pageable pageable);

    RstrEntity findByRstrId(Long RstrId);

//    Page<RstrEntity> findAllByRstrId(Long rstrId, Pageable pageable);

    int countAllByRstrNameContains(String rstrName);

    long count();
}
