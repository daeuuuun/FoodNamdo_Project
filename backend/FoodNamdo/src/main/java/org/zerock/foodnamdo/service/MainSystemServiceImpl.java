package org.zerock.foodnamdo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.foodnamdo.domain.RstrEntity;
import org.zerock.foodnamdo.dto.RstrDTO;
import org.zerock.foodnamdo.repository.MainSystemRepository;
import org.zerock.foodnamdo.repository.MyPageRepository;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MainSystemServiceImpl implements MainSystemService{
    private final MainSystemRepository mainSystemRepository;

    @Override
    public List<RstrEntity> getRestaurantsOrderByRstrReviewCountDesc() {
        return mainSystemRepository.findAllByOrderByRstrReviewCountDesc();
    }

    public List<RstrEntity> findAllByRstrNameContains(String name) {
        List<RstrEntity> rstr = mainSystemRepository.findAllByRstrNameContains(name);
        return rstr;
    }
}
