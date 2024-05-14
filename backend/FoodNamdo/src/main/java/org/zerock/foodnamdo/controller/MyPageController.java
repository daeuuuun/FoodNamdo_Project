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
import org.zerock.foodnamdo.baseDTO.RstrDTO;
import org.zerock.foodnamdo.baseDTO.FavoriteDTO;
import org.zerock.foodnamdo.baseDTO.UserDTO;
import org.zerock.foodnamdo.customDTO.FavoriteTestDTO;
import org.zerock.foodnamdo.domain.FavoriteEntity;
import org.zerock.foodnamdo.domain.ReviewEntity;
import org.zerock.foodnamdo.domain.RstrEntity;
import org.zerock.foodnamdo.domain.UserEntity;
import org.zerock.foodnamdo.service.MyPageService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mypage")
@Tag(name = "MyPageAPI", description = "내 정보 조회, 닉네임 변경, 비밀번호 변경, 도전과제 조회, 뱃지 관리, 찜한 음식점 조회, 작성한 리뷰 조회")
@Log4j2
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

//    @Operation(summary = "내정보 조회")
//    @GetMapping("/myInfo")
//    public void myInfo(){
//
//    }
//
//    @Operation(summary = "닉네임 수정")
//    @PostMapping("/changeNickname")
//    public void changeNickname() {
//
//    }
//
//
//    @Operation(summary = "비밀번호 변경")
//    @PostMapping("/changePassword")
//    public void changePassword() {
//
//    }
//
//
//    @Operation(summary = "뱃지 확인")
//    @GetMapping("/myBadge")
//    public void myBadge() {
//
//    }

    @Operation(summary = "찜한 음식점 조회")
    @GetMapping(value = "/getFavoriteRstr", produces = "application/json")
    @ResponseBody
    public Map<String, Object> getFavoriteRstr(
            @RequestParam("user_id") Long userId,
            @RequestParam("page") int page) {
        log.info("getFavoriteRstr......");
        int pageSize = 8;

        Pageable pageable = PageRequest.of(Math.max(0, page - 1), pageSize);
        Page<FavoriteEntity> userPage = myPageService.findAllByUserEntity_UserId(userId, pageable);
        log.info(userPage);
//        List<FavoriteTestDTO> rstrDTOList = userPage.getContent().stream()
//                .map(FavoriteTestDTO::fromEntity)
//                .collect(Collectors.toList());
        List<RstrDTO> rstrList = userPage.getContent().stream()
                .map(FavoriteTestDTO::fromEntity)
                .map(FavoriteTestDTO::getRstr)
                .collect(Collectors.toList());

        long totalElements = userPage.getTotalElements();

        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("page_size", pageSize);
        response.put("total_pages", totalPages);
        response.put("page_num", page);
        response.put("total_favorite", totalElements);
        response.put("rstr", rstrList);

        return response;
    }
}
