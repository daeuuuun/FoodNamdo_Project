//package org.zerock.foodnamdo.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.zerock.foodnamdo.domain.ReviewEntity;
//import org.zerock.foodnamdo.domain.ReviewImgEntity;
//import org.zerock.foodnamdo.repository.ReviewRepository;
//import org.zerock.foodnamdo.repository.ReviewImgRepository;
//import org.zerock.foodnamdo.service.ReviewImageService;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.UUID;
//
//@Service
//public class ReviewImageServiceImpl implements ReviewImageService {
//
//    @Autowired
//    private ReviewRepository reviewRepository;
//
//    @Autowired
//    private ReviewImgRepository reviewImgRepository;
//
//    private final String uploadDir = "/path/to/upload/directory"; // 실제 업로드 디렉토리 경로
//
//    @Override
//    public String saveReviewImage(Long reviewId, MultipartFile image) {
//        ReviewEntity reviewEntity = reviewRepository.findById(reviewId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid review ID"));
//
//        String originalFilename = image.getOriginalFilename();
//        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
//        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
//
//        try {
//            Path filePath = Paths.get(uploadDir, uniqueFilename);
//            Files.copy(image.getInputStream(), filePath);
//            String imageUrl = "/url/to/access/image/" + uniqueFilename; // 실제 이미지 접근 URL
//
//            ReviewImgEntity reviewImgEntity = ReviewImgEntity.builder()
//                    .reviewEntity(reviewEntity)
//                    .imageUrl(imageUrl)
//                    .build();
//            reviewImgRepository.save(reviewImgEntity);
//
//            return imageUrl;
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to store image", e);
//        }
//    }
//}
