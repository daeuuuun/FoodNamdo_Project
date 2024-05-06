package org.zerock.foodnamdo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.foodnamdo.domain.RstrEntity;

import java.util.List;

public interface MainSystemService {
    Page<RstrEntity> findAllByOrderByRstrReviewCountDesc(Pageable pageable);

    Page<RstrEntity> findAllByRstrNameContains(String name, Pageable pageable);

    int countAllByRstrNameContains(String rstrName);

    long count();
}
