package org.zerock.foodnamdo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.zerock.foodnamdo.baseDTO.ReviewDTO;
import org.zerock.foodnamdo.customDTO.FindReviewByRstrIdDTO;
import org.zerock.foodnamdo.customDTO.FindRstrByNameDTO;
import org.zerock.foodnamdo.domain.ReviewEntity;
import org.zerock.foodnamdo.domain.RstrEntity;
import org.zerock.foodnamdo.service.MainSystemService;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
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


    @Operation(summary = "음식점 목록 조회")
    @GetMapping(value = "/findAll", produces = "application/json")
    @ResponseBody
    public Map<String, Object> findAll(@RequestParam("page") int page) {
        log.info("findAll......");
        int pageSize = 8;

        // 요청된 페이지의 결과를 가져오기 위해 페이지와 페이지 크기로 Pageable 객체를 생성
//        Pageable pageable = PageRequest.of(page, pageSize);
        Pageable pageable = PageRequest.of(Math.max(0, page - 1), pageSize);

        Page<RstrEntity> rstrPage = mainSystemService.findAllByOrderByRstrReviewCountDesc(pageable);
        log.info(rstrPage);
        List<FindRstrByNameDTO> rstrAllDTOList = rstrPage.getContent().stream()
                .map(FindRstrByNameDTO::fromEntity)
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

        return response;
    }

    // 요청된 식당 이름을 포함하는 모든 식당의 DTO 목록을 가져옴
//        List<RstrDTObackup> rstrDTOList = mainSystemService.findAllByOrderByRstrReviewCountDesc(pageable).stream()
//                .map(rstrEntity -> {
//                    String categoryName = ""; // 카테고리 이름 변수 초기화
//
//                    double roundedNaverRating = Math.round(rstrEntity.getRstrNaverRating() * 100.0) / 100.0;
//                    double roundedReviewRating = Math.round(rstrEntity.getRstrReviewRating() * 100.0) / 100.0;
//
//                    // 식당의 카테고리 중 하나 선택
//                    if (rstrEntity.getCategories() != null && !rstrEntity.getCategories().isEmpty()) {
//                        categoryName = rstrEntity.getCategories().iterator().next().getCategoryName();
//                    }
//
//                    // RstrDTO 객체 생성
//                    RstrDTObackup rstrDTO = RstrDTObackup.builder()
//                            .category_name(categoryName)
//                            .rstr_id(rstrEntity.getRstrId())
//                            .rstr_img(rstrEntity.getRstrImages().stream()
//                                    .map(RstrimgEntity -> RstrImgDTO.builder()
//                                            .rstr_img_id(RstrimgEntity.getRstrImgId())
//                                            .rstr_img_url(RstrimgEntity.getRstrImgUrl())
//                                            .build())
//                                    .collect(Collectors.toList()))
//                            .rstr_num(rstrEntity.getRstrNum())
//                            .rstr_region(rstrEntity.getRstrRegion())
//                            .rstr_permission(rstrEntity.getRstrPermission())
//                            .rstr_name(rstrEntity.getRstrName())
//                            .rstr_address(rstrEntity.getRstrAddress())
//                            .rstr_la(rstrEntity.getRstrLa())
//                            .rstr_lo(rstrEntity.getRstrLo())
//                            .rstr_tel(rstrEntity.getRstrTel())
//                            .rstr_intro(rstrEntity.getRstrIntro())
//                            .rstr_naver_rating(roundedNaverRating)
//                            .rstr_review_rating(roundedReviewRating)
//                            .example(rstrEntity.isExample() ? 1 : 0)
//                            .relax(rstrEntity.isRelax() ? 1 : 0)
//                            .rstr_review_count(rstrEntity.getRstrReviewCount())
//                            .rstr_favorite_count(rstrEntity.getRstrFavoriteCount())
//                            .rstr_parking(rstrEntity.isRstrParking() ? 1 : 0)
//                            .rstr_play(rstrEntity.isRstrPlay() ? 1 : 0)
//                            .rstr_pet(rstrEntity.isRstrPet() ? 1 : 0)
//                            .rstr_closed(rstrEntity.getRstrClosed())
//                            .rstr_business_hour(rstrEntity.getRstrBusinessHour())
//                            .rstr_delivery(rstrEntity.isRstrDelivery() ? 1 : 0)
//                            .menu_description(rstrEntity.getMenuDescriptions().stream()
//                                    .map(menuDescriptionEntity -> MenuDescriptionDTO.builder()
//                                            .menu_description_id(menuDescriptionEntity.getMenuDescriptionId())
//                                            .rstr_id(menuDescriptionEntity.getRstrEntity().getRstrId())
//                                            .menu_id(menuDescriptionEntity.getMenuId())
//                                            .menu_category_sub(menuDescriptionEntity.getMenuCategorySub())
//                                            .menu_name(menuDescriptionEntity.getMenuName())
//                                            .menu_price(menuDescriptionEntity.getMenuPrice())
//                                            .build())
//                                    .collect(Collectors.toList()))
//                            .build();
//                    return rstrDTO;
//                })
//                .collect(Collectors.toList());

    // 전체 결과 수 계산

    @Operation(summary = "음식점이름으로 음식점 검색")
    @GetMapping(value = "/findRstrByName", produces = "application/json")
    @ResponseBody
    public Map<String, Object> findRstrByName(
            @RequestParam("rstrName") String rstrName,
            @RequestParam("page") int page) {
        log.info("findRstrByName......");

        int pageSize = 8;

        // 요청된 페이지의 결과를 가져오기 위해 페이지와 페이지 크기로 Pageable 객체를 생성
//        Pageable pageable = PageRequest.of(page, pageSize);
        Pageable pageable = PageRequest.of(Math.max(0, page - 1), pageSize);

        Page<RstrEntity> rstrPage = mainSystemService.findAllByRstrNameContains(rstrName, pageable);
        log.info(rstrPage);
        List<FindRstrByNameDTO> findRstrByNameDTOList = rstrPage.getContent().stream()
                .map(FindRstrByNameDTO::fromEntity)
                .collect(Collectors.toList());

        int totalResults = mainSystemService.countAllByRstrNameContains(rstrName);
        // 전체 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalResults / pageSize);

        // 응답 데이터 생성
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("page_size", pageSize);
        response.put("total_pages", totalPages);
        response.put("page_num", page);
        response.put("total_rstr", totalResults);
        response.put("rstr", findRstrByNameDTOList);

        return response;
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

    // 요청된 식당 이름을 포함하는 모든 식당의 DTO 목록을 가져옴
//        List<RstrDTO> rstrDTOList = mainSystemService.findAllByRstrNameContains(rstrName, pageable).stream()
//                .map(rstrEntity -> {
//                    String categoryName = ""; // 카테고리 이름 변수 초기화
//
//                    double roundedNaverRating = Math.round(rstrEntity.getRstrNaverRating() * 100.0) / 100.0;
//                    double roundedReviewRating = Math.round(rstrEntity.getRstrReviewRating() * 100.0) / 100.0;
//
//                    // 식당의 카테고리 중 하나 선택
//                    if (rstrEntity.getCategories() != null && !rstrEntity.getCategories().isEmpty()) {
//                        categoryName = rstrEntity.getCategories().iterator().next().getCategoryName();
//                    }
//
//                    // RstrDTO 객체 생성
//                    RstrDTO rstrDTO = RstrDTO.builder()
//                            .category_name(categoryName)
//                            .rstr_id(rstrEntity.getRstrId())
//                            .rstr_img(rstrEntity.getRestaurantImages().stream()
//                                    .map(RstrimgEntity -> RstrImgDTO.builder()
//                                            .rstr_img_id(RstrimgEntity.getRstrImgId())
//                                            .rstr_img_url(RstrimgEntity.getRstrImgUrl())
//                                            .build())
//                                    .collect(Collectors.toList()))
//                            .rstr_num(rstrEntity.getRstrNum())
//                            .rstr_region(rstrEntity.getRstrRegion())
//                            .rstr_permission(rstrEntity.getRstrPermission())
//                            .rstr_name(rstrEntity.getRstrName())
//                            .rstr_address(rstrEntity.getRstrAddress())
//                            .rstr_la(rstrEntity.getRstrLa())
//                            .rstr_lo(rstrEntity.getRstrLo())
//                            .rstr_tel(rstrEntity.getRstrTel())
//                            .rstr_intro(rstrEntity.getRstrIntro())
//                            .rstr_naver_rating(roundedNaverRating)
//                            .rstr_review_rating(roundedReviewRating)
//                            .example(rstrEntity.isExample() ? 1 : 0)
//                            .relax(rstrEntity.isRelax() ? 1 : 0)
//                            .rstr_review_count(rstrEntity.getRstrReviewCount())
//                            .rstr_favorite_count(rstrEntity.getRstrFavoriteCount())
//                            .rstr_parking(rstrEntity.isRstrParking() ? 1 : 0)
//                            .rstr_play(rstrEntity.isRstrPlay() ? 1 : 0)
//                            .rstr_pet(rstrEntity.isRstrPet() ? 1 : 0)
//                            .rstr_closed(rstrEntity.getRstrClosed())
//                            .rstr_business_hour(rstrEntity.getRstrBusinessHour())
//                            .rstr_delivery(rstrEntity.isRstrDelivery() ? 1 : 0)
//                            .menu_description(rstrEntity.getMenuDescriptions().stream()
//                                    .map(menuDescriptionEntity -> MenuDescriptionDTO.builder()
//                                            .menu_description_id(menuDescriptionEntity.getMenuDescriptionId())
//                                            .rstr_id(menuDescriptionEntity.getRestaurant().getRstrId())
//                                            .menu_id(menuDescriptionEntity.getMenuId())
//                                            .menu_category_sub(menuDescriptionEntity.getMenuCategorySub())
//                                            .menu_name(menuDescriptionEntity.getMenuName())
//                                            .menu_price(menuDescriptionEntity.getMenuPrice())
//                                            .build())
//                                    .collect(Collectors.toList()))
//                            .build();
//                    return rstrDTO;
//                })
//                .collect(Collectors.toList());

    // 전체 결과 수 계산


}
