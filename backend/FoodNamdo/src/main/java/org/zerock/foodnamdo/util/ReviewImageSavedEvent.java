package org.zerock.foodnamdo.util;

public class ReviewImageSavedEvent {
    private final int reviewImgId;

    public ReviewImageSavedEvent(int reviewImgId) {
        this.reviewImgId = reviewImgId;
    }

    public int getReviewImgId() {
        return reviewImgId;
    }
}

