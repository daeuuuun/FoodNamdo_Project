package org.zerock.foodnamdo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.foodnamdo.domain.ReviewEntity;
import org.zerock.foodnamdo.domain.RstrEntity;
import org.zerock.foodnamdo.repository.MainSystemRepositoryReview;
import org.zerock.foodnamdo.repository.MainSystemRepositoryRstr;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MainSystemServiceImpl implements MainSystemService{
    private final MainSystemRepositoryRstr mainSystemRepositoryRstr;
    private final MainSystemRepositoryReview mainSystemRepositoryReview;

    @Override
    public Page<RstrEntity> findAllByOrderByRstrReviewCountDesc(Pageable pageable) {
        Page<RstrEntity> rstr = mainSystemRepositoryRstr.findAllByOrderByRstrReviewCountDesc(pageable);
        return rstr;
    }

    public Page<RstrEntity> findAllByRstrNameContains(String name, Pageable pageable) {
        return mainSystemRepositoryRstr.findAllByRstrNameContains(name, pageable);
//        Page<RstrEntity> rstr = mainSystemRepository.findAllByRstrNameContains(name, pageable);
//        return rstr;
    }

//    public Page<RstrEntity> findAllByRstrId(Long rstrId, Pageable pageable) {
//        return mainSystemRepositoryRstr.findAllByRstrId(rstrId, pageable);
//    }
    public Page<ReviewEntity> findAllByRstrEntity_RstrId(Long rstrId, Pageable pageable) {
//    public Page<ReviewEntity> findAllByRstrEntity(Long rstrId, Pageable pageable) {
        return mainSystemRepositoryReview.findAllByRstrEntity_RstrId(rstrId, pageable);
    }

    public RstrEntity findByRstrId(Long rstrId) {
        return mainSystemRepositoryRstr.findByRstrId(rstrId);
    }


    public int countAllByRstrNameContains(String rstrName) {
        int count = mainSystemRepositoryRstr.countAllByRstrNameContains(rstrName);
        return count;
    }

    public long count() {
        long count = mainSystemRepositoryRstr.count();
        return count;
    }
}
