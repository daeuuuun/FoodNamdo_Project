package org.zerock.foodnamdo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.foodnamdo.customDTO.*;
import org.zerock.foodnamdo.domain.*;
import org.zerock.foodnamdo.repository.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MainSystemServiceImpl implements MainSystemService{

    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;
    private final MainSystemRepositoryRstr mainSystemRepositoryRstr;
    private final MainSystemRepositoryReview mainSystemRepositoryReview;
    private final MainSystemRepositoryUser mainSystemRepositoryUser;
    private final MainSystemRepositoryFavorite mainSystemRepositoryFavirote;
    private final MainSystemRepositoryReviewImg mainSystemRepositoryReviewImg;
    private final MainSystemRepositoryReaction mainSystemRepositoryReaction;
    private final MainSystemRepositoryBadge mainSystemRepositoryBadge;
    private final RestTemplate restTemplate;
    private final MainSystemRepositoryUserBadge mainSystemRepositoryUserBadge;




    @Override
    public Page<RstrEntity> findAll(Pageable pageable) {
        return mainSystemRepositoryRstr.findAll(pageable);
    }

    @Override
    public Page<RstrEntity> findAllByOrderByRstrReviewRatingDesc(Pageable pageable) {
        return mainSystemRepositoryRstr.findAllByOrderByRstrReviewRatingDesc(pageable);
    }

    @Override
    public Page<RstrEntity> findAllByOrderByRstrReviewCountDesc(Pageable pageable) {
        return mainSystemRepositoryRstr.findAllByOrderByRstrReviewCountDesc(pageable);
    }

    @Override
    public List<RstrEntity> findAllByOrderByRstrReviewRatingDesc() {
        return mainSystemRepositoryRstr.findAllByOrderByRstrReviewRatingDesc();
    }

    @Override
    public Page<RstrEntity> findAllByOrderByRstrFavoriteCountDesc(Pageable pageable) {
        return mainSystemRepositoryRstr.findAllByOrderByRstrFavoriteCountDesc(pageable);
    }

    @Override
    public List<RstrEntity> findAllByOrderByRstrFavoriteCountDesc() {
        return mainSystemRepositoryRstr.findAllByOrderByRstrFavoriteCountDesc();
    }

    public ReviewEntity findRstrByReviewId(Long reviewId) {
        return mainSystemRepositoryReview.findByReviewId(reviewId);
    }

//    public RstrEntity findByReviewEntity_reviewId(Long reviewId){
//        return mainSystemRepositoryRstr.findByReviewEntity_reviewId(reviewId);
//    }

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

    public Optional<ReviewEntity> findByReviewId(Long reviewId){
        return mainSystemRepositoryReview.findById(reviewId);
    }

    public String getRstrName_ReviewEntity(Long reviewId){
        return mainSystemRepositoryReview.findByReviewId(reviewId).getRstrEntity().getRstrName();
    }

    public void updateLastVisit(Long userId, Long rstrId){
        mainSystemRepositoryUser.updateLastVisit(userId, rstrId);
    }



    public long count() {
        return mainSystemRepositoryRstr.count();
    }

    @Override
    public Long reviewBadgeUpdate(Long userId){
        int Count = mainSystemRepositoryReview.findAllByUserEntity_UserId(userId).size();
        Long[] badgeIds = {1L, 2L, 3L, 4L, 5L};
        int[] thresholds = {1, 5, 10, 20, 30};

        return BadgeUpdate(userId, Count, badgeIds, thresholds);
    }

    @Override
    public Long reactionBadgeUpdate(Long userId){
        int Count = mainSystemRepositoryReaction.findAllByUserEntity_UserId(userId).size();
        Long[] badgeIds = {6L, 7L, 8L, 9L, 10L};
        int[] thresholds = {1, 5, 10, 20, 30};

        return BadgeUpdate(userId, Count, badgeIds, thresholds);
    }

    @Override
    public Long receiptBadgeUpdate(Long userId) {
        int Count = mainSystemRepositoryReview.findAllByUserEntity_UserIdAndReceiptTrue(userId).size();
        Long[] badgeIds = {11L, 12L, 13L, 14L, 15L};
        int[] thresholds = {1, 3, 5, 10, 15};

        return BadgeUpdate(userId, Count, badgeIds, thresholds);
    }

    @Override
    public Long allBadgeUpdate(Long userId){
        int Count = mainSystemRepositoryUserBadge.findAllByUserEntity_UserId(userId).size();
        Long[] badgeIds = {21L, 22L, 23L, 24L, 25L, 26L};
        int[] thresholds = {1, 3, 5, 10, 15, 19};

        return BadgeUpdate(userId, Count, badgeIds, thresholds);
    }

    private Long BadgeUpdate(Long userId, int Count, Long[] badgeIds, int[] thresholds){
        for (int i = 0; i < thresholds.length; i++) {
            if (Count >= thresholds[i]) {
                Long badgeId = badgeIds[i];
                Optional<UserBadgeEntity> existingBadge = mainSystemRepositoryUserBadge.findByUserEntity_UserIdAndBadgeEntity_BadgeId(userId, badgeId);

                if (existingBadge.isEmpty()) {
                    UserEntity userEntity = mainSystemRepositoryUser.findById(userId)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
                    BadgeEntity badgeEntity = mainSystemRepositoryBadge.findById(badgeId)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid badge ID"));

                    UserBadgeEntity userBadgeEntity = UserBadgeEntity.builder()
                            .userEntity(userEntity)
                            .badgeEntity(badgeEntity)
                            .badgeOnOff(false)
                            .build();

                    mainSystemRepositoryUserBadge.save(userBadgeEntity);
                    return badgeId;
                }
            }
        }

        return null;
    }



    @Override
    public void insertReviewReaction(ReactionReviewDTO reactionReviewDTO){
        ReviewEntity reviewEntity = mainSystemRepositoryReview.findById(reactionReviewDTO.getReviewId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid review ID"));
        UserEntity userEntity = mainSystemRepositoryUser.findById(reactionReviewDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        ReactionEntity existingReaction = mainSystemRepositoryReaction.findByReviewEntityAndUserEntity(reviewEntity, userEntity);

        if (existingReaction != null) {
            if (existingReaction.getReactionType() == reactionReviewDTO.getReactionType()) {
                mainSystemRepositoryReaction.delete(existingReaction);
            } else {
                existingReaction.setReactionType(reactionReviewDTO.getReactionType());
                mainSystemRepositoryReaction.save(existingReaction);
            }
        } else {
            ReactionEntity reactionEntity = ReactionEntity.builder()
                    .reviewEntity(reviewEntity)
                    .userEntity(userEntity)
                    .reactionType(reactionReviewDTO.getReactionType())
                    .build();
            mainSystemRepositoryReaction.save(reactionEntity);
        }
//        ReactionEntity reactionEntity = ReactionEntity.builder()
//                .reviewEntity(reviewEntity)
//                .userEntity(userEntity)
//                .reactionType(reactionReviewDTO.getReactionType())
//                .build();
//
//        mainSystemRepositoryReaction.save(reactionEntity);
        updateReviewReactions(reviewEntity);
    }

//    @Override
    public void updateReviewReactions(ReviewEntity reviewEntity){

        long likeCount = mainSystemRepositoryReaction.countByReviewEntityAndReactionType(reviewEntity, ReactionType.LIKE);
        long dislikeCount = mainSystemRepositoryReaction.countByReviewEntityAndReactionType(reviewEntity, ReactionType.DISLIKE);

//        ReviewEntity reviewEntity = mainSystemRepositoryReview.findById(reviewId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid review ID"));

        reviewEntity.setLike((int) likeCount);
        reviewEntity.setDislike((int) dislikeCount);

        mainSystemRepositoryReview.save(reviewEntity);

    }
    @Override
    public Long saveReviewAndReturnId(ReviewRegisterDTO reviewRegisterDTO) {
//    public void saveReview(ReviewRegisterDTO reviewRegisterDTO){
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
                .receipt(reviewRegisterDTO.isReceipt())
                .like(reviewRegisterDTO.getLike())
                .dislike(reviewRegisterDTO.getDislike())
                .build();

        mainSystemRepositoryReview.save(reviewEntity);





        updateRestaurantRatingAndCount(reviewRegisterDTO.getRstr_id());
        return reviewEntity.getReviewId();

//        if (reviewRegisterDTO.getReviewImages() != null && !reviewRegisterDTO.getReviewImages().isEmpty()) {
//            for (String imageUrl : reviewRegisterDTO.getReviewImages()) {
//                ReviewImgEntity reviewImgEntity = ReviewImgEntity.builder()
//                        .reviewEntity(reviewEntity)
//                        .reviewImgUrl(imageUrl)
//                        .build();
//                mainSystemRepositoryReviewImg.save(reviewImgEntity);
//            }
//        }
    }

//    public void assignBadgesBasedOnReviewCount(Long userId) {
//        int reviewCount = mainSystemRepositoryReview.findAllByUserEntity_UserId(userId).size();
//        List<Integer> badgeThresholds = Arrays.asList(1, 5, 10, 20, 30);
//
//        for (int threshold : badgeThresholds) {
//            if (reviewCount >= threshold) {
//                BadgeEntity badgeEntity = mainSystemRepositoryBadge.findById((long) threshold)
//                        .orElseThrow(() -> new IllegalArgumentException("Invalid badge ID"));
//                UserBadgeEntity userBadgeEntity = new UserBadgeEntity();
//                userBadgeEntity.setUserEntity(mainSystemRepositoryUser.findById(userId)
//                        .orElseThrow(() -> new IllegalArgumentException("Invalid user ID")));
//                userBadgeEntity.setBadgeEntity(badgeEntity);
//                mainSystemRepositoryUserBadge.save(userBadgeEntity);
//            }
//        }
//    }

    @Override
    @Transactional
    public void updateReview(ReviewUpdateDTO reviewUpdateDTO, Long userId) {
        ReviewEntity reviewEntity = mainSystemRepositoryReview.findById(reviewUpdateDTO.getReview_id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid review ID"));

        if (!reviewEntity.getUserEntity().getUserId().equals(userId)) {
            throw new SecurityException("You are not authorized to update this review");
        }

        if (reviewUpdateDTO.getReview_text() != null) {
            reviewEntity.setReviewText(reviewUpdateDTO.getReview_text());
        }
        if (reviewUpdateDTO.getCategory_rating_taste() != null) {
            reviewEntity.setCategoryRatingTaste(reviewUpdateDTO.getCategory_rating_taste());
        }
        if (reviewUpdateDTO.getCategory_rating_price() != null) {
            reviewEntity.setCategoryRatingPrice(reviewUpdateDTO.getCategory_rating_price());
        }
        if (reviewUpdateDTO.getCategory_rating_clean() != null) {
            reviewEntity.setCategoryRatingClean(reviewUpdateDTO.getCategory_rating_clean());
        }
        if (reviewUpdateDTO.getCategory_rating_service() != null) {
            reviewEntity.setCategoryRatingService(reviewUpdateDTO.getCategory_rating_service());
        }
        if (reviewUpdateDTO.getCategory_rating_amenities() != null) {
            reviewEntity.setCategoryRatingAmenities(reviewUpdateDTO.getCategory_rating_amenities());
        }
        reviewEntity.setTimeOfRevision(reviewUpdateDTO.getTime_of_revision());

        double rating = (reviewUpdateDTO.getCategory_rating_taste() + reviewUpdateDTO.getCategory_rating_price() +
                reviewUpdateDTO.getCategory_rating_clean() + reviewUpdateDTO.getCategory_rating_service() +
                reviewUpdateDTO.getCategory_rating_amenities()) / 5;

        DecimalFormat df = new DecimalFormat("#.##");
        rating = Double.parseDouble(df.format(rating));

        reviewEntity.setRating(rating);

        mainSystemRepositoryReview.save(reviewEntity);
        updateRestaurantRatingAndCount(reviewEntity.getRstrEntity().getRstrId());
    }

    @Transactional
    public void updateRestaurantRatingAndCount(Long rstrId) {
        RstrEntity rstrEntity = mainSystemRepositoryRstr.findById(rstrId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid restaurant ID"));

        List<ReviewEntity> reviews = mainSystemRepositoryReview.findByRstrEntity(rstrEntity);
        int reviewCount = reviews.size();
        double totalRating = reviews.stream().mapToDouble(ReviewEntity::getRating).sum();
        double averageRating = reviewCount > 0 ? totalRating / reviewCount : 0;

        rstrEntity.setRstrReviewCount(reviewCount);
        rstrEntity.setRstrReviewRating(averageRating);

        mainSystemRepositoryRstr.save(rstrEntity);
    }



    private final String uploadUrl = "http://foodnamdo.iptime.org:8000/images/?path=review_img&api_key=d0f535568d149afec1933a8c37c765e0";

    @Override
    public void saveReviewImage(Long reviewId, MultipartFile image) throws IOException {
        ReviewEntity reviewEntity = mainSystemRepositoryReview.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid review ID"));

        String imageUrl;
        try {
            imageUrl = uploadImageToServer(image);
        } catch (IOException e) {
            // 로깅 등 추가적인 예외 처리
            throw new IOException("Image upload failed", e);
        }

        ReviewImgEntity reviewImgEntity = ReviewImgEntity.builder()
                .reviewEntity(reviewEntity)
                .reviewImgUrl(imageUrl)
                .build();
        mainSystemRepositoryReviewImg.save(reviewImgEntity);

        int reviewImgId = Math.toIntExact(reviewImgEntity.getReviewImgId());
        log.info("reviewImgId: {}", reviewImgId);

        postReviewImage(reviewImgId);

    }

    @Override
    public void postReviewImage(int reviewImgId) throws IOException {
        // Use the internal Docker network address
//        String urlString = "http://foodnamdo.iptime.org/image_search_recommend/review_image/" + reviewImgId;
        String urlString = "http://image_search_recommend:8000/review_image/" + reviewImgId;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", "application/json");

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP request failed with response code " + responseCode);
        }

        // Clean up
        connection.disconnect();
    }

    @Override
    public JsonNode getRecommand(Long userId) throws IOException {
        // Use the internal Docker network address
//        String urlString = "http://foodnamdo.iptime.org/image_search_recommend/recommend/" + userId + "?n_results=5";
        String urlString = "http://image_search_recommend:8000/recommend/" + userId + "?n_results=5";
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Clean up
            in.close();
            connection.disconnect();

            // Parse JSON response
            return objectMapper.readTree(content.toString());
        } else {
            throw new IOException("HTTP request failed with response code " + responseCode);
        }
    }

    private String uploadImageToServer(MultipartFile image) throws IOException {
        String boundary = "===" + System.currentTimeMillis() + "===";
        HttpURLConnection connection = (HttpURLConnection) new URL(uploadUrl).openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        String originalFilename = image.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + "-" + originalFilename;

        try (OutputStream outputStream = connection.getOutputStream();
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true)) {

            writer.append("--").append(boundary).append("\r\n");
            writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(originalFilename).append("\"\r\n");
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
            return "http://foodnamdo.iptime.org:8000/images/?image_name=" + originalFilename + "&path=review_img";
        } else {
            throw new IOException("Failed to upload image. Server returned HTTP response code: " + responseCode);
        }
    }

    private static final String API_URL = "https://kyxg7x7hj6.apigw.ntruss.com/custom/v1/31347/0e807b837caa93532d7555da356de802287917fdce11479ae93e2438a5d9781c/document/receipt";
    private static final String SECRET_KEY = "T0Z1bm9iZE1PZktza1JOaWJTTlBvcnplQ2ZSRlFpR1E=";

    @Override
    public String processOCR(MultipartFile file) {
//    public ResponseEntity<String> processOCR(MultipartFile file) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setReadTimeout(30000);
            con.setRequestMethod("POST");
            String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            con.setRequestProperty("X-OCR-SECRET", SECRET_KEY);

            JSONObject json = new JSONObject();
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());
            JSONObject image = new JSONObject();
            image.put("format", "jpg");
            image.put("name", "demo");
            JSONArray images = new JSONArray();
            images.put(image);
            json.put("images", images);
            String postParams = json.toString();

            con.connect();
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            File tempFile = File.createTempFile("upload", file.getOriginalFilename());
            file.transferTo(tempFile);
            writeMultiPart(wr, postParams, tempFile, boundary);
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            log.info("response: {}", response.toString());

            // JSON 파싱 및 DTO로 변환
            JSONObject responseObj = new JSONObject(response.toString());
            log.info("responseObj: {}", responseObj.toString());
            JSONArray imagesArray = responseObj.getJSONArray("images");
            log.info("imagesArray: {}", imagesArray.toString());
            JSONObject imageObj = imagesArray.getJSONObject(0);
            log.info("imageObj: {}", imageObj.toString());
            JSONObject receiptObj = imageObj.getJSONObject("receipt");
            log.info("receiptObj: {}", receiptObj.toString());
            JSONObject resultObj = receiptObj.getJSONObject("result");
            log.info("resultObj: {}", resultObj.toString());


//            if (resultObj.has("storeInfo")) {
            JSONObject storeInfoObj = resultObj.getJSONObject("storeInfo");
            log.info("storeInfoObj: {}", storeInfoObj.toString());

            String storeName = storeInfoObj.getJSONObject("name").getString("text");
            JSONObject storeNameObj = storeInfoObj.getJSONObject("name");
            String storeNameFormmattedValue = storeNameObj.getJSONObject("formatted").getString("value");
            String subName = storeInfoObj.optJSONObject("subName") != null ? storeInfoObj.getJSONObject("subName").getString("text") : "N/A";
            String bizNum = storeInfoObj.getJSONObject("bizNum").getString("text");
            String address = storeInfoObj.getJSONArray("addresses").getJSONObject(0).getString("text");
            String tel = storeInfoObj.getJSONArray("tel").getJSONObject(0).getString("text");

            log.info("Store Info:");
            log.info("Name: {}", storeName);
            log.info("NameValue: {}", storeNameFormmattedValue);
            log.info("Sub Name: {}", subName);
            log.info("Business Number: {}", bizNum);
            log.info("Address: {}", address);
            log.info("Telephone: {}", tel);
//            } else {
//                log.warn("No storeInfo found in the response.");
//            }

            return storeName;
//            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return "can't get reciept info";
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static void writeMultiPart(OutputStream out, String jsonMessage, File file, String boundary) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("--").append(boundary).append("\r\n");
        sb.append("Content-Disposition:form-data; name=\"message\"\r\n\r\n");
        sb.append(jsonMessage);
        sb.append("\r\n");

        out.write(sb.toString().getBytes("UTF-8"));
        out.flush();

        if (file != null && file.isFile()) {
            out.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
            StringBuilder fileString = new StringBuilder();
            fileString.append("Content-Disposition:form-data; name=\"file\"; filename=");
            fileString.append("\"").append(file.getName()).append("\"\r\n");
            fileString.append("Content-Type: application/octet-stream\r\n\r\n");
            out.write(fileString.toString().getBytes("UTF-8"));
            out.flush();

            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }
                out.write("\r\n".getBytes());
            }

            out.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));
        }
        out.flush();
    }

    public void verifyReview(Long reviewId){
        ReviewEntity reviewEntity = mainSystemRepositoryReview.findByReviewId(reviewId);
        reviewEntity.setReceipt(true);
    }


    public boolean checkFavorite(Long rstrId, Long userId){
        Optional<FavoriteEntity> existingFavorite = mainSystemRepositoryFavirote.findByRstrEntity_RstrIdAndUserEntity_UserId(
                rstrId, userId);

        return existingFavorite.isPresent();
    }

    public boolean saveFavorite(RstrFavoriteRegisterDTO rstrFavoriteRegisterDTO) {
        UserEntity userEntity = mainSystemRepositoryUser.findById(rstrFavoriteRegisterDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        RstrEntity rstrEntity = mainSystemRepositoryRstr.findById(rstrFavoriteRegisterDTO.getRstrId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid restaurant ID"));

        Optional<FavoriteEntity> existingFavorite = mainSystemRepositoryFavirote.findByRstrEntity_RstrIdAndUserEntity_UserId(
                rstrFavoriteRegisterDTO.getRstrId(), rstrFavoriteRegisterDTO.getUserId());

        boolean isFavoriteAdded;
        if (existingFavorite.isPresent()) {
            mainSystemRepositoryFavirote.delete(existingFavorite.get());
            isFavoriteAdded = false;
        } else {
            FavoriteEntity favorite = FavoriteEntity.builder()
                    .userEntity(userEntity)
                    .rstrEntity(rstrEntity)
                    .build();
            mainSystemRepositoryFavirote.save(favorite);
            isFavoriteAdded = true;
        }

        updateRestaurantFavoriteCount(rstrFavoriteRegisterDTO.getRstrId());
        return isFavoriteAdded;
    }

    @Transactional
    public void updateRestaurantFavoriteCount(Long rstrId) {
        RstrEntity rstrEntity = mainSystemRepositoryRstr.findById(rstrId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid restaurant ID"));

        List<FavoriteEntity> favoriteEntities = mainSystemRepositoryFavirote.findByRstrEntity(rstrEntity);
        int favoriteCount = favoriteEntities.size();

        rstrEntity.setRstrFavoriteCount(favoriteCount);

        mainSystemRepositoryRstr.save(rstrEntity);
    }

    @Override
    public void deleteByReviewId(Long reviewId) {
        mainSystemRepositoryReview.deleteAllByReviewId(reviewId);
//        updateRestaurantRatingAndCount();
    }

    @Override
    public void deleteReviewImg(Long reviewId) {
        mainSystemRepositoryReviewImg.deleteAllByReviewEntity_ReviewId(reviewId);
    }

    @Override
    public void deleteReactionByReviewId(Long reviewId) {mainSystemRepositoryReaction.deleteAllByReviewEntity_ReviewId(reviewId);}
}
