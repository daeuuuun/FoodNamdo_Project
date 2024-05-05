package org.zerock.foodnamdo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.foodnamdo.domain.RstrEntity;
import org.zerock.foodnamdo.domain.UserEntity;

import java.util.List;

@Repository
public interface MainSystemRepository extends JpaRepository<RstrEntity, Long> {
    List<RstrEntity> findAllByOrderByRstrReviewCountDesc();

    List<RstrEntity> findAllByRstrNameContains(String name);
}
