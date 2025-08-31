package org.zerock.foodnamdo.service;

import org.springframework.web.multipart.MultipartFile;

public interface ReviewImageService {
    String saveReviewImage(Long reviewId, MultipartFile image);
}