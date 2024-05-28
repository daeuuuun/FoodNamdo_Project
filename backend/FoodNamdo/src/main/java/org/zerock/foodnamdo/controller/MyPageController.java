package org.zerock.foodnamdo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.zerock.foodnamdo.baseDTO.*;
import org.zerock.foodnamdo.customDTO.ChangeNicknameDTO;
import org.zerock.foodnamdo.customDTO.ChangePasswordDTO;
import org.zerock.foodnamdo.customDTO.getFavoriteRstrDTO;
import org.zerock.foodnamdo.domain.*;
import org.zerock.foodnamdo.service.MyPageService;
import org.zerock.foodnamdo.util.JWTUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mypage")
@Tag(name = "MyPageAPI", description = "내 정보 조회, 닉네임 변경, 비밀번호 변경, 도전과제 조회, 뱃지 관리, 찜한 음식점 조회, 작성한 리뷰 조회")
@Log4j2
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;



//    @Operation(summary = "내정보 조회", security = @SecurityRequirement(name = "jwtAuth"))
//    @GetMapping(value = "/myInfo", produces = "application/json")
//    public UserDTO myInfo(@RequestParam("user_id") Long userId){
//        log.info("myInfo......");
//        UserEntity userEntity = myPageService.findByUserId(userId);
//        return UserDTO.fromEntity(userEntity);
//    }

    @Operation(summary = "내정보 조회", security = @SecurityRequirement(name = "jwtAuth"))
    @GetMapping(value = "/myInfo", produces = "application/json")
//    public UserDTO myInfo(@RequestHeader("Authorization") String token) {
    public UserDTO myInfo2() {
        log.info("myInfo......");


//        String userIdStr = userDetails.getUsername();
        APIUserDTO userDetails = (APIUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getUserId();  // Extract userId
        log.info(userDetails);

        log.info(userId);

        UserEntity userEntity = myPageService.findByUserId(userId);
        return UserDTO.fromEntity(userEntity);
    }

    @Operation(summary = "닉네임 변경")
    @PostMapping("/changeNickname")
    public ResponseEntity<String> changeNickname(
//            @RequestHeader("user_id") Long userId,
            @RequestBody ChangeNicknameDTO changeNicknameDTO) {
//            @RequestParam("nickname") String nickname) {
        APIUserDTO userDetails = (APIUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getUserId();  // Extract userId
        String nickname = changeNicknameDTO.getNickname();


        myPageService.changeNickname(userId, nickname);
        return ResponseEntity.ok("Nickname changed successfully");
    }


    @Operation(summary = "비밀번호 변경")
    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(
//            @RequestParam("user_id") Long userId,
//            @RequestParam("password") String password) {
            @RequestBody ChangePasswordDTO changePasswordDTO) {
        log.info("changePassword......");
        APIUserDTO userDetails = (APIUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getUserId();  // Extract userId
        String prevPassword = changePasswordDTO.getPrevPassword();
        UserEntity userEntity = myPageService.findByUserId(userId);
        String DBPassword = userEntity.getPassword();
        log.info("prevPassword: " + prevPassword);
        log.info("DBPassword: " + DBPassword);
        if(passwordEncoder.matches(prevPassword, DBPassword)){
            String newPassword = passwordEncoder.encode(changePasswordDTO.getNewPassword());
            myPageService.changePassword(userId, newPassword);
            return ResponseEntity.ok("Password changed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Current password does not match");
        }
    }

    @Operation(summary = "사용자id로 리뷰 불러오기")
    @GetMapping(value = "/getReview", produces = "application/json")
    public List<ReviewDTO> getReview() {
//    public List<ReviewDTO> getReview(@RequestParam("user_id") Long userId) {

        APIUserDTO userDetails = (APIUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getUserId();  // Extract userId
        log.info("getReview......");
        List<ReviewEntity> reviewEntity = myPageService.findReviewByUserId(userId);
        return reviewEntity.stream()
                .map(ReviewDTO::fromEntity)
                .toList();
    }

    @Operation(summary = "뱃지 확인")
    @GetMapping(value = "/myBadge", produces = "application/json")
    public Map<String, Object> myBadge() {
//    public List<UserBadgeDTO> myBadge() {
        APIUserDTO userDetails = (APIUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getUserId();  // Extract userId
        log.info("getReview......");
        List<BadgeEntity> badgeEntity = myPageService.getAllBadges();
        List<BadgeDTO> BadgeDTOList = badgeEntity.stream()
                .map(BadgeDTO::fromEntity)
                .toList();

        List<UserBadgeEntity> userBadgeEntity = myPageService.findUserBadgeByUserId(userId);
        List<UserBadgeDTO> userBadgeDTOList = userBadgeEntity.stream()
                .map(UserBadgeDTO::fromEntity)
                .toList();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("badge", BadgeDTOList);
        response.put("userBadge", userBadgeDTOList);

        return response;
//        return userBadgeDTOList;
//        return userBadgeEntity.stream()
//                .map(UserBadgeDTO::fromEntity)
//                .toList();
    }

    @Operation(summary = "찜한 음식점 조회")
    @GetMapping(value = "/getFavoriteRstr", produces = "application/json")
    @ResponseBody
    public Map<String, Object> getFavoriteRstr(
//            @RequestParam("user_id") Long userId,
            @RequestParam("page") int page) {
        APIUserDTO userDetails = (APIUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getUserId();  // Extract userId
        log.info("getFavoriteRstr......");
        int pageSize = 8;

        Pageable pageable = PageRequest.of(Math.max(0, page - 1), pageSize);
        Page<FavoriteEntity> userPage = myPageService.findAllByUserEntity_UserId(userId, pageable);
        log.info(userPage);
        List<getFavoriteRstrDTO> getFavoriteRstrDTOList = userPage.getContent().stream()
                .map(getFavoriteRstrDTO::fromEntity)
                .collect(Collectors.toList());

        long totalElements = userPage.getTotalElements();

        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("page_size", pageSize);
        response.put("total_pages", totalPages);
        response.put("page_num", page);
        response.put("total_favorite", totalElements);
        response.put("rstr", getFavoriteRstrDTOList);

        return response;
    }
}
