package org.zerock.foodnamdo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.foodnamdo.domain.RstrEntity;
import org.zerock.foodnamdo.repository.MainSystemRepository;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MainSystemServiceImpl implements MainSystemService{
    private final MainSystemRepository mainSystemRepository;

    @Override
    public Page<RstrEntity> findAllByOrderByRstrReviewCountDesc(Pageable pageable) {
        Page<RstrEntity> rstr = mainSystemRepository.findAllByOrderByRstrReviewCountDesc(pageable);
        return rstr;
    }

    public Page<RstrEntity> findAllByRstrNameContains(String name, Pageable pageable) {
        return mainSystemRepository.findAllByRstrNameContains(name, pageable);
//        Page<RstrEntity> rstr = mainSystemRepository.findAllByRstrNameContains(name, pageable);
//        return rstr;
    }

    public int countAllByRstrNameContains(String rstrName) {
        int count = mainSystemRepository.countAllByRstrNameContains(rstrName);
        return count;
    }

    public long count() {
        long count = mainSystemRepository.count();
        return count;
    }
}
