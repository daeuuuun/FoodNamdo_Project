package org.zerock.foodnamdo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.foodnamdo.customDTO.ReviewRegisterDTO;
import org.zerock.foodnamdo.customDTO.RstrFavoriteRegisterDTO;
import org.zerock.foodnamdo.domain.*;
import org.zerock.foodnamdo.repository.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MainSystemServiceImpl implements MainSystemService{

    private final ModelMapper modelMapper;
    private final MainSystemRepositoryRstr mainSystemRepositoryRstr;
    private final MainSystemRepositoryReview mainSystemRepositoryReview;
    private final MainSystemRepositoryUser mainSystemRepositoryUser;
    private final MainSystemRepositoryFavorite mainSystemRepositoryFavirote;
    private final MainSystemRepositoryReviewImg mainSystemRepositoryReviewImg;

    @Override
    public Page<RstrEntity> findAllByOrderByRstrReviewCountDesc(Pageable pageable) {
        Page<RstrEntity> rstr = mainSystemRepositoryRstr.findAllByOrderByRstrReviewCountDesc(pageable);
        return rstr;
    }

    public Page<RstrEntity> findAllByRstrNameContainsAndFilters(String name, String category, String region, Pageable pageable) {
        return mainSystemRepositoryRstr.findByRstrNameContainsAndFilters(name, category, region, pageable);
        //        return mainSystemRepositoryRstr.findAllByRstrNameContains(name, pageable);
    }
    public Page<ReviewEntity> findAllByRstrEntity_RstrId(Long rstrId, Pageable pageable) {
//    public Page<ReviewEntity> findAllByRstrEntity(Long rstrId, Pageable pageable) {
        return mainSystemRepositoryReview.findAllByRstrEntity_RstrId(rstrId, pageable);
    }

    public RstrEntity findByRstrId(Long rstrId) {
        return mainSystemRepositoryRstr.findByRstrId(rstrId);
    }


    public long count() {
        long count = mainSystemRepositoryRstr.count();
        return count;
    }
    public void saveReview(ReviewRegisterDTO reviewRegisterDTO){
        RstrEntity rstrEntity = mainSystemRepositoryRstr.findById(reviewRegisterDTO.getRstr_id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid restaurant ID"));
        UserEntity userEntity = mainSystemRepositoryUser.findById(reviewRegisterDTO.getUser_id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

//        ReviewEntity reviewEntity = reviewRegisterDTO.toEntity(rstrEntity, userEntity);

        ReviewEntity reviewEntity = ReviewEntity.builder()
                .rstrEntity(rstrEntity)
                .userEntity(userEntity)
                .reviewText(reviewRegisterDTO.getReview_text())
                .timeOfCreation(reviewRegisterDTO.getTime_of_creation())
                .timeOfRevision(null)
                .rating(reviewRegisterDTO.getRating())
                .categoryRatingTaste(reviewRegisterDTO.getCategory_rating_taste())
                .categoryRatingPrice(reviewRegisterDTO.getCategory_rating_price())
                .categoryRatingClean(reviewRegisterDTO.getCategory_rating_clean())
                .categoryRatingService(reviewRegisterDTO.getCategory_rating_service())
                .categoryRatingAmenities(reviewRegisterDTO.getCategory_rating_amenities())
                .receipt(false)
                .like(0)
                .dislike(0)
                .build();

        mainSystemRepositoryReview.save(reviewEntity);

        if (reviewRegisterDTO.getReviewImages() != null && !reviewRegisterDTO.getReviewImages().isEmpty()) {
            for (String imageUrl : reviewRegisterDTO.getReviewImages()) {
                ReviewImgEntity reviewImgEntity = ReviewImgEntity.builder()
                        .reviewEntity(reviewEntity)
                        .reviewImgUrl(imageUrl)
                        .build();
                mainSystemRepositoryReviewImg.save(reviewImgEntity);
            }
        }
    }
    private final String uploadUrl = "http://foodnamdo.iptime.org:8000/images/?path=review_img&api_key=d0f535568d149afec1933a8c37c765e0";

    @Override
    public String saveReviewImage(Long reviewId, MultipartFile image) throws IOException {
        String boundary = "===" + System.currentTimeMillis() + "===";
        HttpURLConnection connection = (HttpURLConnection) new URL(uploadUrl).openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        // 파일 이름 추출
        String originalFilename = image.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + "-" + image.getOriginalFilename();
        String imageUrl = "/uploads/" + fileName;

        try (OutputStream outputStream = connection.getOutputStream();
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true)) {

            // 파일 파트 추가
//            String fileName = image.getOriginalFilename();
            writer.append("--").append(boundary).append("\r\n");
            writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(fileName).append("\"\r\n");
            writer.append("Content-Type: ").append(image.getContentType()).append("\r\n");
            writer.append("\r\n").flush();

            InputStream inputStream = image.getInputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();

            writer.append("\r\n").flush();
            writer.append("--").append(boundary).append("--").append("\r\n").flush();
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (InputStream responseStream = connection.getInputStream()) {
                String responseBody = new String(responseStream.readAllBytes(), StandardCharsets.UTF_8);


                String DBImgUrl = "http://foodnamdo.iptime.org:8000/images/?image_name=" + originalFilename + "&path=review_img";


                ReviewEntity reviewEntity = mainSystemRepositoryReview.findById(reviewId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid review ID"));

                ReviewImgEntity reviewImgEntity = ReviewImgEntity.builder()
                        .reviewEntity(reviewEntity)
                        .reviewImgUrl(DBImgUrl)
                        .build();

                mainSystemRepositoryReviewImg.save(reviewImgEntity);

                return responseBody;
            }
        } else {
            throw new IOException("Failed to upload image. Server returned HTTP response code: " + responseCode);
        }
    }

    public void saveFavorite(RstrFavoriteRegisterDTO rstrFavoriteRegisterDTO) {
        UserEntity userEntity = mainSystemRepositoryUser.findById(rstrFavoriteRegisterDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        RstrEntity rstrEntity = mainSystemRepositoryRstr.findById(rstrFavoriteRegisterDTO.getRstrId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid restaurant ID"));

        FavoriteEntity favorite = FavoriteEntity.builder()
                .userEntity(userEntity)
                .rstrEntity(rstrEntity)
                .build();

        mainSystemRepositoryFavirote.save(favorite);
    }

    @Override
    public void deleteByReviewId(Long reviewId) {
        mainSystemRepositoryReview.deleteByReviewId(reviewId);
    }
}
