package org.zerock.foodnamdo.service;

import org.zerock.foodnamdo.domain.RstrEntity;
import org.zerock.foodnamdo.dto.RstrDTO;

import java.util.List;

public interface MainSystemService {
    List<RstrEntity> getRestaurantsOrderByRstrReviewCountDesc();

    List<RstrEntity> findAllByRstrNameContains(String name);
}
