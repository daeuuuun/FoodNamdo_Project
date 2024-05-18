package org.zerock.foodnamdo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

    @Operation(summary = "내정보 조회")
    @GetMapping(value = "/myInfo", produces = "application/json")
//    public UserEntity myInfo(@RequestParam("user_id") Long userId){
    public UserDTO myInfo(@RequestParam("user_id") Long userId){
        log.info("myInfo......");
        UserEntity userEntity = myPageService.findByUserId(userId);
//        UserDTO userDTO = myPageService.findByUserId(userId);

//        return userEntity;
        return UserDTO.fromEntity(userEntity);
    }
//
    @Operation(summary = "닉네임 변경")
    @PostMapping("/changeNickname")
    public ResponseEntity<String> changeNickname(
            @RequestParam("user_id") Long userId,
            @RequestParam("nickname") String nickname) {
        log.info("changeNickname......");
        myPageService.changeNickname(userId, nickname);
        return ResponseEntity.ok("Nickname changed successfully");
    }

    @Operation(summary = "비밀번호 변경")
    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(
            @RequestParam("user_id") Long userId,
            @RequestParam("password") String password) {
        log.info("changeNickname......");
        myPageService.changePassword(userId, password);
        return ResponseEntity.ok("Password changed successfully");
    }

    @Operation(summary = "사용자id로 리뷰 불러오기")
    @GetMapping(value = "/getReview", produces = "application/json")
    public List<ReviewDTO> getReview(
            @RequestParam("user_id") Long userId) {
        log.info("getReview......");
        List<ReviewEntity> reviewEntity = myPageService.findReviewByUserId(userId);
        return reviewEntity.stream()
                .map(ReviewDTO::fromEntity)
                .toList();
    }

//    @PostMapping
//    public String modify(PageRequestDTO pageRequestDTO,
//                         @Valid UserDTO userDTO,
//                         BindingResult bindingResult,
//                         RedirectAttributes redirectAttributes) {
//        log.info("user modify post.........." + userDTO);
//
//        if (bindingResult.hasErrors()) {
//            log.info("has errors.......");
//
//            String link = pageRequestDTO.getLink();
//
//            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
//
//            redirectAttributes.addAttribute("userId", userDTO.getUserId());
//
//            return "redirect:/user/modify?" + link;
//        }
//
//        userManagementService.modify(userDTO);
//
//        redirectAttributes.addFlashAttribute("result", "modified");
//
//        redirectAttributes.addAttribute("userId", userDTO.getUserId());
//
//        return "redirect:/user/read";
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
