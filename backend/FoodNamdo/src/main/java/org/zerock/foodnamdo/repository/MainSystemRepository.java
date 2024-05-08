package org.zerock.foodnamdo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.foodnamdo.domain.RstrEntity;

import java.util.List;

@Repository
public interface MainSystemRepository extends JpaRepository<RstrEntity, Long> {
    Page<RstrEntity> findAllByOrderByRstrReviewCountDesc(Pageable pageable);

    Page<RstrEntity> findAllByRstrNameContains(String name, Pageable pageable);

    int countAllByRstrNameContains(String rstrName);

    long count();
}
