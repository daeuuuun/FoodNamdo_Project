package org.zerock.foodnamdo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.foodnamdo.baseDTO.APIUserDTO;
import org.zerock.foodnamdo.baseDTO.ReviewDTO;
import org.zerock.foodnamdo.customDTO.*;
import org.zerock.foodnamdo.domain.ReviewEntity;
import org.zerock.foodnamdo.domain.RstrEntity;
import org.zerock.foodnamdo.service.MainSystemService;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

//<<<<<<< HEAD
////@CrossOrigin(origins = "http://localhost:3000")
//=======
//// @CrossOrigin(origins = "http://localhost:3000")
//>>>>>>> a2a71ab70f483efda05179e42e4c56c905afd505
@RestController
@RequestMapping("/mainsystem")
@Tag(name = "MainSystemAPI", description =
        "음식점 목록 조회, 음식점 텍스트 검색, 음식점 상세 조회, 음식점 찜하기," +
                " 리뷰 조회, 리뷰 등록, 리뷰 수정, 리뷰 인증, 리뷰 삭제, 리뷰 공감," +
                " 음식점 이미지 검색, 음식점 개인 맞춤 추천")
@Log4j2
@RequiredArgsConstructor
public class MainSystemController {
    private final MainSystemService mainSystemService;

    @Operation(summary = "메인페이지 음식점 목록")
    @GetMapping(value = "/mainRstr", produces = "application/json")
    @ResponseBody
    public Map<String, Object> mainRstr() throws IOException {
        log.info("mainRstr......");
        int page = 1;
        int pageSize = 3;

        Pageable pageable = PageRequest.of(Math.max(0, page - 1), pageSize);

        APIUserDTO userDetails = (APIUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getUserId();  // Extract userId
        JsonNode rstrRecommandList = mainSystemService.getRecommand(userId);

        Page<RstrEntity> rstrReviewList = mainSystemService.findAllByOrderByRstrReviewRatingDesc(pageable);
        Page<RstrEntity> rstrFavoriteList = mainSystemService.findAllByOrderByRstrFavoriteCountDesc(pageable);

        List<FindAllRstrDTO> rstrReviewAllDTOList = rstrReviewList.getContent().stream()
                .map(FindAllRstrDTO::fromEntity)
                .collect(Collectors.toList());

        List<FindAllRstrDTO> rstrFavoriteAllDTOList = rstrFavoriteList.getContent().stream()
                .map(FindAllRstrDTO::fromEntity)
                .collect(Collectors.toList());

        // 응답 데이터 생성
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("rstr_recommand", rstrRecommandList);
        response.put("rstr_favorite", rstrFavoriteAllDTOList);
        response.put("rstr_review", rstrReviewAllDTOList);

        return response;
    }

    @Operation(summary = "메인페이지 음식점 목록(로그인x)")
    @GetMapping(value = "/mainRstrNoToken", produces = "application/json")
    @ResponseBody
    public Map<String, Object> mainRstrNoToken() throws IOException {
        log.info("mainRstrNoToken......");
        int page = 1;
        int pageSize = 3;

        Pageable pageable = PageRequest.of(Math.max(0, page - 1), pageSize);

        JsonNode rstrRecommandList = null;

        Page<RstrEntity> rstrReviewList = mainSystemService.findAllByOrderByRstrReviewRatingDesc(pageable);
        Page<RstrEntity> rstrFavoriteList = mainSystemService.findAllByOrderByRstrFavoriteCountDesc(pageable);

        List<FindAllRstrDTO> rstrReviewAllDTOList = rstrReviewList.getContent().stream()
                .map(FindAllRstrDTO::fromEntity)
                .collect(Collectors.toList());

        List<FindAllRstrDTO> rstrFavoriteAllDTOList = rstrFavoriteList.getContent().stream()
                .map(FindAllRstrDTO::fromEntity)
                .collect(Collectors.toList());

        // 응답 데이터 생성
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("rstr_recommand", rstrRecommandList);
        response.put("rstr_favorite", rstrFavoriteAllDTOList);
        response.put("rstr_review", rstrReviewAllDTOList);

        return response;
    }


    @Operation(summary = "음식점 목록 조회")
    @GetMapping(value = "/findAll", produces = "application/json")
    @ResponseBody
    public Map<String, Object> findAll(@RequestParam("page") int page,
                                       @RequestParam(value = "sort", required = false)
                                       @Parameter(description = "정렬", schema = @Schema(allowableValues = {"기본순", "리뷰높은순", "리뷰많은순", "찜많은순"})) String sort) {
        log.info("findAll......");
        int pageSize = 8;

        Pageable pageable = PageRequest.of(Math.max(0, page - 1), pageSize);

        Page<RstrEntity> rstrPage = null;
        if(Objects.equals(sort, "기본순")){
            rstrPage = mainSystemService.findAll(pageable);
        } else if (Objects.equals(sort, "리뷰높은순")) {
            rstrPage = mainSystemService.findAllByOrderByRstrReviewRatingDesc(pageable);
        } else if (Objects.equals(sort, "리뷰많은순")) {
            rstrPage = mainSystemService.findAllByOrderByRstrReviewCountDesc(pageable);
        } else if (Objects.equals(sort, "찜많은순")) {
            rstrPage = mainSystemService.findAllByOrderByRstrFavoriteCountDesc(pageable);
        }

        List<FindAllRstrDTO> rstrAllDTOList = rstrPage.getContent().stream()
                .map(FindAllRstrDTO::fromEntity)
                .collect(Collectors.toList());


        long totalResults = mainSystemService.count();
        // 전체 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalResults / pageSize);

        // 응답 데이터 생성
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("page_size", pageSize);
        response.put("total_pages", totalPages);
        response.put("page_num", page);
        response.put("total_rstr", totalResults);
        response.put("rstr", rstrAllDTOList);
//        response.put("rstr_favorite", rstrFavoriteAllDTOList);
//        response.put("rstr_review", rstrReviewAllDTOList);

        return response;
    }


    // 전체 결과 수 계산

    @Operation(summary = "음식점이름으로 음식점 검색")
    @GetMapping(value = "/findRstrByName", produces = "application/json")
    @ResponseBody
    public Map<String, Object> findRstrByName(
            @RequestParam("rstrName") String rstrName,
            @RequestParam("page") int page,
            @RequestParam(value = "category", required = false)
            @Parameter(description = "음식점 카테고리",
                    schema = @Schema(allowableValues = {"전체", "한식", "중식", "일식", "카페/제과점", "양식", "치킨/호프", "분식", "식육(숯불구이)"
                            , "회", "패스트푸드", "푸드트럭", "외국음식전문점", "뷔페식", "기타"})) String category,
            @RequestParam(value = "region", required = false)
            @Parameter(description = "지역", schema = @Schema(allowableValues = {"전체", "경상남도", "전라남도"})) String region,
            @RequestParam(value = "sort", required = false)
            @Parameter(description = "정렬", schema = @Schema(allowableValues = {"distance", "rstr_review_rating", "rstr_review_count", "rstr_favorite_count"})) String sort) {
        log.info("findRstrByName......");

        category = "전체".equals(category) ? null : category;
        region = "전체".equals(region) ? null : region;

        int pageSize = 8;

        Sort sortOrder = Sort.unsorted();
        if ("rstr_review_rating".equals(sort)) {
            sortOrder = Sort.by(Sort.Order.desc("rstrReviewRating"));
        } else if ("rstr_review_count".equals(sort)) {
            sortOrder = Sort.by(Sort.Order.desc("rstrReviewCount"));
        } else if ("rstr_favorite_count".equals(sort)) {
            sortOrder = Sort.by(Sort.Order.desc("rstrFavoriteCount"));
        }

        Pageable pageable = PageRequest.of(Math.max(0, page - 1), pageSize, sortOrder);

        Page<RstrEntity> rstrPage = mainSystemService.findAllByRstrNameContainsAndFilters(rstrName, category, region, pageable);
        log.info(rstrPage);
        List<FindRstrByNameDTO> findRstrByNameDTOList = rstrPage.getContent().stream()
                .map(FindRstrByNameDTO::fromEntity)
                .collect(Collectors.toList());

        int totalResults = (int) rstrPage.getTotalElements();
        int totalPages = (int) Math.ceil((double) totalResults / pageSize);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("page_size", pageSize);
        response.put("total_pages", totalPages);
        response.put("page_num", page);
        response.put("total_rstr", totalResults);
        response.put("rstr", findRstrByNameDTOList);

        return response;
    }

    @Operation(summary = "음식점 상세 조회")
    @GetMapping(value = "/RstrDetail", produces = "application/json")
    @ResponseBody
    public RstrDetailDTO RstrDetail(
            @RequestParam("rstr_id") Long rstrId) {
        log.info("RstrDetail......");

        RstrEntity rstrEntity = mainSystemService.findByRstrId(rstrId);

        try {
            APIUserDTO userDetails = (APIUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId = userDetails.getUserId();  // Extract userId
            log.info("userId: " + userId);

            mainSystemService.updateLastVisit(userId, rstrId);
            log.info("last visit updated.");
        } catch (Exception e) {
            log.info("User is not authenticated, proceeding without updating last visit.");
        }

        return RstrDetailDTO.fromEntity(rstrEntity);
    }

    @Operation(summary = "음식점 상세 조회(로그인x)")
    @GetMapping(value = "/RstrDetailNoToken", produces = "application/json")
    @ResponseBody
    public RstrDetailDTO RstrDetailNoToken(
            @RequestParam("rstr_id") Long rstrId) {
        log.info("RstrDetail......");

        RstrEntity rstrEntity = mainSystemService.findByRstrId(rstrId);

        return RstrDetailDTO.fromEntity(rstrEntity);
    }

    @Operation(summary = "음식점ID로 해당 음식점 전체 리뷰 조회")
    @GetMapping(value = "/getReviewByRstrId", produces = "application/json")
    @ResponseBody
    public Map<String, Object> getReviewByRstrId(
            @RequestParam("rstr_id") Long rstrId,
            @RequestParam("page") int page) {
        log.info("getReviewByRstrId......");
        int pageSize = 8;

        Pageable pageable = PageRequest.of(Math.max(0, page - 1), pageSize);
        Page<ReviewEntity> reviewPage = mainSystemService.findAllByRstrEntity_RstrId(rstrId, pageable);
//        Page<RstrEntity> reviewPage = mainSystemService.findAllByRstrId(rstrId, pageable);
        log.info(reviewPage);
//        List<FindReviewByRstrIdDTO> findReviewByRstrIdDTOList = reviewPage.getContent().stream()
//                .map(FindReviewByRstrIdDTO::fromEntity)
//                .collect(Collectors.toList());
        List<ReviewDTO> reviewDTOList = reviewPage.getContent().stream()
                .map(ReviewDTO::fromEntity)
                .collect(Collectors.toList());

        long totalElements = reviewPage.getTotalElements();

        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("page_size", pageSize);
        response.put("total_pages", totalPages);
        response.put("page_num", page);
        response.put("total_review", totalElements);
        response.put("reviews", reviewDTOList);

        return response;
    }

    @Operation(summary = "음식점 찜하기")
    @PostMapping(value = "/RstrFavoriteRegister", produces = "application/json")
    public boolean RstrFavoriteRegister(@RequestBody RstrFavoriteRegisterInputDTO rstrFavoriteRegisterInputDTO){
        log.info("RstrFavoriteRegister..");
        APIUserDTO userDetails = (APIUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getUserId();  // Extract userId

        // userId와 rstrId를 사용하여 RstrFavoriteRegisterDTO 생성
        RstrFavoriteRegisterDTO rstrFavoriteRegisterDTO = new RstrFavoriteRegisterDTO();
        rstrFavoriteRegisterDTO.setUserId(userId);
        rstrFavoriteRegisterDTO.setRstrId(rstrFavoriteRegisterInputDTO.getRstrId());

        // rstrFavoriteRegisterDTO를 사용하여 추가적인 처리 (예: 데이터베이스에 저장)
        boolean isFavoriteAdded = mainSystemService.saveFavorite(rstrFavoriteRegisterDTO);

        log.info("Favorite registered for userId: {} and rstrId: {}", userId, rstrFavoriteRegisterInputDTO.getRstrId());

        return isFavoriteAdded;
    }

    @Operation(summary = "음식점 찜확인")
    @GetMapping(value = "/RstrFavoriteRegister", produces = "application/json")
    public boolean RstrFavoriteCheck(@RequestParam ("rstr_id") Long rstrId){
        log.info("RstrFavoriteCheck..");
        APIUserDTO userDetails = (APIUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getUserId();  // Extract userId

        return mainSystemService.checkFavorite(rstrId, userId);
    }



    @Operation(summary = "리뷰등록")
    @PostMapping("/ReviewRegister")
    public ResponseEntity<Long> ReviewRegister(
//    public String ReviewRegister(
            @RequestBody ReviewRegisterInputDTO reviewRegisterInputDTO
            ) {
//            RedirectAttributes redirectAttributes) {
        log.info("ReviewRegister..");
        APIUserDTO userDetails = (APIUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getUserId();  // Extract userId

        double categoryRatingTaste = reviewRegisterInputDTO.getCategory_rating_taste();
        double categoryRatingPrice = reviewRegisterInputDTO.getCategory_rating_price();
        double categoryRatingClean = reviewRegisterInputDTO.getCategory_rating_clean();
        double categoryRatingService = reviewRegisterInputDTO.getCategory_rating_service();
        double categoryRatingAmenities = reviewRegisterInputDTO.getCategory_rating_amenities();

        DecimalFormat df = new DecimalFormat("#.##");
        double rating = (categoryRatingTaste + categoryRatingPrice + categoryRatingClean
                + categoryRatingService + categoryRatingAmenities) / 5;
        rating = Double.parseDouble(df.format(rating));

        ReviewRegisterDTO reviewRegisterDTO = new ReviewRegisterDTO();
        reviewRegisterDTO.setRstr_id(reviewRegisterInputDTO.getRstr_id());
        reviewRegisterDTO.setUser_id(userId);
        reviewRegisterDTO.setReview_text(reviewRegisterInputDTO.getReview_text());
        reviewRegisterDTO.setTime_of_creation(reviewRegisterInputDTO.getTime_of_creation());
        reviewRegisterDTO.setTime_of_revision(null);
        reviewRegisterDTO.setRating(rating);
        reviewRegisterDTO.setCategory_rating_taste(categoryRatingTaste);
        reviewRegisterDTO.setCategory_rating_price(categoryRatingPrice);
        reviewRegisterDTO.setCategory_rating_clean(categoryRatingClean);
        reviewRegisterDTO.setCategory_rating_service(categoryRatingService);
        reviewRegisterDTO.setCategory_rating_amenities(categoryRatingAmenities);
        reviewRegisterDTO.setReceipt(false);
        reviewRegisterDTO.setLike(0);
        reviewRegisterDTO.setDislike(0);
//        reviewRegisterDTO.setReviewImages(null);



        Long reviewId = mainSystemService.saveReviewAndReturnId(reviewRegisterDTO);

        Long reviewBadgeId = mainSystemService.reviewBadgeUpdate(userId);
        if (reviewBadgeId != null) {
            System.out.println("Badge ID " + reviewBadgeId + " granted to user " + userId);
        }
        Long totalBadgeId = mainSystemService.allBadgeUpdate(userId);
        if (totalBadgeId != null) {
            System.out.println("Total Badge ID " + totalBadgeId + " granted to user " + userId);
        }
        return ResponseEntity.ok(reviewId);
//        mainSystemService.saveReview(reviewRegisterDTO);
//
//
//        redirectAttributes.addFlashAttribute("result", "created");
//
//        return redirectAttributes.toString();
    }


    @Operation(summary = "리뷰 이미지 업로드")
    @PostMapping(value = "/uploadReviewImage", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadReviewImage(
            @RequestParam("review_id") Long reviewId,
            @Parameter(name = "file", description = "업로드 사진 데이터")
            @RequestParam("file") MultipartFile image) {
        log.info("uploadReviewImage..");

        try {
            mainSystemService.saveReviewImage(reviewId, image);
            return ResponseEntity.ok("이미지 업로드 및 저장 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 업로드 실패: " + e.getMessage());
        }
    }

    @Operation(summary = "리뷰이미지id전송")
    @PostMapping("/postReviewImage")
    public ResponseEntity<String> postReviewImage(@RequestParam int reviewImgId) {
        try {
            mainSystemService.postReviewImage(reviewImgId);
            return ResponseEntity.ok("Successfully posted review image");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to post review image: " + e.getMessage());
        }
    }


    @Operation(summary = "리뷰수정")
    @PostMapping("/modifyReview")
    public void modifyReview(@RequestBody ReviewUpdateDTO reviewUpdateDTO) {
        log.info("updateReview..");
        APIUserDTO userDetails = (APIUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getUserId();  // Extract userId

        mainSystemService.updateReview(reviewUpdateDTO, userId);
    }

//    @Operation(summary = "리뷰인증")
//    @PostMapping(value = "/verifyReview", consumes = "multipart/form-data", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<OcrResponseDTO> verifyReview(
//            @RequestParam("review_id") Long reviewId,
//            @Parameter(name = "file", description = "업로드 사진 데이터")
//            @RequestParam("file") MultipartFile image) {
//        log.info("verifyReview..");
//        APIUserDTO userDetails = (APIUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Long userId = userDetails.getUserId();  // Extract userId
//
//        try {
//            OcrResponseDTO ocrResponse = mainSystemService.verifyreview(userId, reviewId, image);
//
//            Long receiptBadgeId = mainSystemService.receiptBadgeUpdate(userId);
//            if (receiptBadgeId != null) {
//                log.info("Badge ID " + receiptBadgeId + " granted to user " + userId);
//            }
//            Long totalBadgeId = mainSystemService.allBadgeUpdate(userId);
//            if (totalBadgeId != null) {
//                log.info("Total Badge ID " + totalBadgeId + " granted to user " + userId);
//            }
//
//            return ResponseEntity.ok(ocrResponse);
//        } catch (IOException | JSONException e) {
//            log.error("Exception in verifyReview", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }


    @Operation(summary = "리뷰인증")
    @PostMapping(value = "/verifyReview", consumes = "multipart/form-data")
//    @PostMapping(value = "/upload", consumes = "multipart/form-data", produces = "application/json")
    public String verifyReview(
            @RequestParam("review_id") Long reviewId,
            @Parameter(name = "file", description = "업로드 사진 데이터")
            @RequestParam("file") MultipartFile file) {
        log.info("verifyReview..");

        APIUserDTO userDetails = (APIUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getUserId();  // Extract userId

        String recieptRstrName = mainSystemService.processOCR(file);
        String rstrName = "COSTCO";
//        String rstrName = mainSystemService.getRstrName_ReviewEntity(reviewId);

        if(recieptRstrName.equals(rstrName)){
            mainSystemService.verifyReview(reviewId);
        }

        Long receiptBadgeId = mainSystemService.receiptBadgeUpdate(userId);
        if (receiptBadgeId != null) {
            log.info("Badge ID " + receiptBadgeId + " granted to user " + userId);
        }
        Long totalBadgeId = mainSystemService.allBadgeUpdate(userId);
        if (totalBadgeId != null) {
            log.info("Total Badge ID " + totalBadgeId + " granted to user " + userId);
        }

        String check = recieptRstrName + "," + rstrName;
        return check;
    }

    @Operation(summary = "리뷰공감")
    @PostMapping("/reactionReview")
    public void reactionReview(@RequestBody ReactionReviewInputDTO reactionReviewInputDTO){
        log.info("reactionReview..");
        APIUserDTO userDetails = (APIUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getUserId();  // Extract userId

        ReactionReviewDTO reactionReviewDTO = new ReactionReviewDTO();
        reactionReviewDTO.setReviewId(reactionReviewInputDTO.getReviewId());
        reactionReviewDTO.setUserId(userId);
        reactionReviewDTO.setReactionType(reactionReviewInputDTO.getReactionType());

        mainSystemService.insertReviewReaction(reactionReviewDTO);

        Long reactionBadgeId = mainSystemService.reactionBadgeUpdate(userId);
        if (reactionBadgeId != null) {
            System.out.println("Badge ID " + reactionBadgeId + " granted to user " + userId);
        }
        Long totalBadgeId = mainSystemService.allBadgeUpdate(userId);
        if (totalBadgeId != null) {
            System.out.println("Total Badge ID " + totalBadgeId + " granted to user " + userId);
        }
    }

    @Operation(summary = "리뷰삭제")
    @PostMapping("/deleteReview")
    public String deleteReview(@RequestParam("review_id") Long reviewId, RedirectAttributes redirectAttributes) {
        log.info("delete user.." + reviewId);

//        ReviewEntity reviewEntity = mainSystemService.findRstrByReviewId(reviewId);
//
//        Long rstrId = reviewEntity.getRstrEntity().getRstrId();
//
//        RstrEntity rstrEntity = mainSystemService.findByRstrId(rstrId);

        mainSystemService.deleteReactionByReviewId(reviewId);

        mainSystemService.deleteReviewImg(reviewId);

        mainSystemService.deleteByReviewId(reviewId);

        redirectAttributes.addFlashAttribute("result", "deleted");

        return redirectAttributes.toString();
    }

}
