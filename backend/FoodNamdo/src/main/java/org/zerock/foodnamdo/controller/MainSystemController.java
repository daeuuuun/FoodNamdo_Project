package org.zerock.foodnamdo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.foodnamdo.domain.RstrEntity;
import org.zerock.foodnamdo.dto.MenuDescriptionDTO;
import org.zerock.foodnamdo.dto.RstrDTO;
import org.zerock.foodnamdo.dto.RstrResponseDTO;
import org.zerock.foodnamdo.dto.RstrWithMenuDTO;
import org.zerock.foodnamdo.service.MainSystemService;

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
    @GetMapping("/findAll")
    public ResponseEntity<Map<String, Object>> findAll() {
        log.info("findAll......");
        List<RstrDTO> rstrDTOListReview = mainSystemService.getRestaurantsOrderByRstrReviewCountDesc().stream()
                .map(rstrEntity -> {
                    RstrDTO rstrDTO = RstrDTO.builder()
                            .rstrId(rstrEntity.getRstrId())
                            .rstrNum(rstrEntity.getRstrNum())
                            .rstrRegion(rstrEntity.getRstrRegion())
                            .rstrPermission(rstrEntity.getRstrPermission())
                            .rstrName(rstrEntity.getRstrName())
                            .rstrAddress(rstrEntity.getRstrAddress())
                            .rstrLa(rstrEntity.getRstrLa())
                            .rstrLo(rstrEntity.getRstrLo())
                            .rstrTel(rstrEntity.getRstrTel())
                            .rstrIntro(rstrEntity.getRstrIntro())
                            .rstrNaverRating(rstrEntity.getRstrNaverRating())
                            .rstrReviewRating(rstrEntity.getRstrReviewRating())
                            .example(rstrEntity.isExample() ? 1 : 0)
                            .relax(rstrEntity.isRelax() ? 1 : 0)
                            .rstrReviewCount(rstrEntity.getRstrReviewCount())
                            .rstrFavoriteCount(rstrEntity.getRstrFavoriteCount())
                            .rstrParking(rstrEntity.isRstrParking() ? 1 : 0)
                            .rstrPlay(rstrEntity.isRstrPlay() ? 1 : 0)
                            .rstrPet(rstrEntity.isRstrPet() ? 1 : 0)
                            .rstrClosed(rstrEntity.getRstrClosed())
                            .rstrBusinessHour(rstrEntity.getRstrBusinessHour())
                            .rstrDelivery(rstrEntity.isRstrDelivery() ? 1 : 0)
                            .menuDescriptions(rstrEntity.getMenuDescriptions().stream()
                                    .map(menuDescriptionEntity -> MenuDescriptionDTO.builder()
                                            .menuDescriptionId(menuDescriptionEntity.getMenuDescriptionId())
                                            .rstrId(menuDescriptionEntity.getRestaurant().getRstrId())
                                            .menuId(menuDescriptionEntity.getMenuId())
                                            .menuCategorySub(menuDescriptionEntity.getMenuCategorySub())
                                            .menuName(menuDescriptionEntity.getMenuName())
                                            .menuPrice(menuDescriptionEntity.getMenuPrice())
                                            .build())
                                    .collect(Collectors.toList()))
                            .build();
                    return rstrDTO;
                })
                .collect(Collectors.toList());

        int pageSize = 8;
        int totalResults = rstrDTOListReview.size();
        int totalPages = (int) Math.ceil((double) totalResults / pageSize);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("page_size", pageSize);
        response.put("total_pages", totalPages);
        response.put("rstr", rstrDTOListReview);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


//    @GetMapping(value = "/findRstrByName", produces = "application/json")
//    public List<RstrDTO> findRstrByName(@RequestParam("rstrName") String rstrName) {
//        List<RstrDTO> rstrDTOList = mainSystemService.findAllByRstrNameContains(rstrName).stream()
//                .map(rstrEntity -> {
//                    RstrDTO rstrDTO = RstrDTO.builder()
//                            .rstrId(rstrEntity.getRstrId())
//                            .rstrNum(rstrEntity.getRstrNum())
//                            .rstrRegion(rstrEntity.getRstrRegion())
//                            .rstrPermission(rstrEntity.getRstrPermission())
//                            .rstrName(rstrEntity.getRstrName())
//                            .rstrAddress(rstrEntity.getRstrAddress())
//                            .rstrLa(rstrEntity.getRstrLa())
//                            .rstrLo(rstrEntity.getRstrLo())
//                            .rstrTel(rstrEntity.getRstrTel())
//                            .rstrIntro(rstrEntity.getRstrIntro())
//                            .rstrNaverRating(rstrEntity.getRstrNaverRating())
//                            .rstrReviewRating(rstrEntity.getRstrReviewRating())
//                            .example(rstrEntity.isExample() ? 1 : 0)
//                            .relax(rstrEntity.isRelax() ? 1 : 0)
//                            .rstrFavoriteCount(rstrEntity.getRstrFavoriteCount())
//                            .rstrParking(rstrEntity.isRstrParking() ? 1 : 0)
//                            .rstrPlay(rstrEntity.isRstrPlay() ? 1 : 0)
//                            .rstrPet(rstrEntity.isRstrPet() ? 1 : 0)
//                            .rstrClosed(rstrEntity.getRstrClosed())
//                            .rstrBusinessHour(rstrEntity.getRstrBusinessHour())
//                            .rstrDelivery(rstrEntity.isRstrDelivery() ? 1 : 0)
//                            .menuDescriptions(rstrEntity.getMenuDescriptions().stream()
//                                    .map(menuDescriptionEntity -> MenuDescriptionDTO.builder()
//                                            .menuDescriptionId(menuDescriptionEntity.getMenuDescriptionId())
//                                            .rstrId(menuDescriptionEntity.getRestaurant().getRstrId())
//                                            .menuId(menuDescriptionEntity.getMenuId())
//                                            .menuCategorySub(menuDescriptionEntity.getMenuCategorySub())
//                                            .menuName(menuDescriptionEntity.getMenuName())
//                                            .menuPrice(menuDescriptionEntity.getMenuPrice())
//                                            .build())
//                                    .collect(Collectors.toList()))
//                            .build();
//                    return rstrDTO;
//                })
//                .collect(Collectors.toList());
//
//        return rstrDTOList;
//    }

    @GetMapping(value = "/findRstrByName", produces = "application/json")
    public ResponseEntity<Map<String, Object>> findRstrByName(@RequestParam("rstrName") String rstrName) {
        List<RstrDTO> rstrDTOList = mainSystemService.findAllByRstrNameContains(rstrName).stream()
                .map(rstrEntity -> {
                    RstrDTO rstrDTO = RstrDTO.builder()
                            .rstrId(rstrEntity.getRstrId())
                            .rstrNum(rstrEntity.getRstrNum())
                            .rstrRegion(rstrEntity.getRstrRegion())
                            .rstrPermission(rstrEntity.getRstrPermission())
                            .rstrName(rstrEntity.getRstrName())
                            .rstrAddress(rstrEntity.getRstrAddress())
                            .rstrLa(rstrEntity.getRstrLa())
                            .rstrLo(rstrEntity.getRstrLo())
                            .rstrTel(rstrEntity.getRstrTel())
                            .rstrIntro(rstrEntity.getRstrIntro())
                            .rstrNaverRating(rstrEntity.getRstrNaverRating())
                            .rstrReviewRating(rstrEntity.getRstrReviewRating())
                            .example(rstrEntity.isExample() ? 1 : 0)
                            .relax(rstrEntity.isRelax() ? 1 : 0)
                            .rstrReviewCount(rstrEntity.getRstrReviewCount())
                            .rstrFavoriteCount(rstrEntity.getRstrFavoriteCount())
                            .rstrParking(rstrEntity.isRstrParking() ? 1 : 0)
                            .rstrPlay(rstrEntity.isRstrPlay() ? 1 : 0)
                            .rstrPet(rstrEntity.isRstrPet() ? 1 : 0)
                            .rstrClosed(rstrEntity.getRstrClosed())
                            .rstrBusinessHour(rstrEntity.getRstrBusinessHour())
                            .rstrDelivery(rstrEntity.isRstrDelivery() ? 1 : 0)
                            .menuDescriptions(rstrEntity.getMenuDescriptions().stream()
                                    .map(menuDescriptionEntity -> MenuDescriptionDTO.builder()
                                            .menuDescriptionId(menuDescriptionEntity.getMenuDescriptionId())
                                            .rstrId(menuDescriptionEntity.getRestaurant().getRstrId())
                                            .menuId(menuDescriptionEntity.getMenuId())
                                            .menuCategorySub(menuDescriptionEntity.getMenuCategorySub())
                                            .menuName(menuDescriptionEntity.getMenuName())
                                            .menuPrice(menuDescriptionEntity.getMenuPrice())
                                            .build())
                                    .collect(Collectors.toList()))
                            .build();
                    return rstrDTO;
                })
                .collect(Collectors.toList());

        int pageSize = 8;
        int totalResults = rstrDTOList.size();
        int totalPages = (int) Math.ceil((double) totalResults / pageSize);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("page_size", pageSize);
        response.put("total_pages", totalPages);
        response.put("rstr", rstrDTOList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
