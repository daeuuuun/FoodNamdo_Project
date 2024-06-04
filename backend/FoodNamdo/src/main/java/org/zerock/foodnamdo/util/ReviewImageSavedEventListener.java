package org.zerock.foodnamdo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.zerock.foodnamdo.service.MainSystemService;

import java.io.IOException;

@Component
public class ReviewImageSavedEventListener {

    @Autowired
    private MainSystemService mainSystemService; // postReviewImage 메소드를 포함하는 서비스의 빈을 주입

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onReviewImageSaved(ReviewImageSavedEvent event) {
        try {
            mainSystemService.postReviewImage(event.getReviewImgId());
        } catch (IOException e) {
            // 로깅 또는 추가적인 예외 처리
            e.printStackTrace();
        }
    }
}

