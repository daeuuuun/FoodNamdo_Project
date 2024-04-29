package org.zerock.foodnamdo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.foodnamdo.service.MyPageService;

@RestController
@RequestMapping("/mypage")
@Tag(name = "MyPageAPI", description = "내 정보 조회, 닉네임 변경, 비밀번호 변경, 도전과제 조회, 뱃지 관리, 찜한 음식점 조회, 작성한 리뷰 조회")
@Log4j2
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @Operation(summary = "내정보 조회")
    @GetMapping("/myInfo")
    public void myInfo(){

    }

    @Operation(summary = "닉네임 수정")
    @PostMapping("/changeNickname")
    public void changeNickname() {

    }


    @Operation(summary = "비밀번호 변경")
    @PostMapping("/changePassword")
    public void changePassword() {

    }


    @Operation(summary = "")
    @GetMapping("/myBadge")
    public void myBadge() {

    }

}
