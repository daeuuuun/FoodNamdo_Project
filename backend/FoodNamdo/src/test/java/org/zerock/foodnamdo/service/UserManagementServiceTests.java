//package org.zerock.foodnamdo.service;
//
//import lombok.extern.log4j.Log4j2;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.zerock.foodnamdo.dto.PageRequestDTO;
//import org.zerock.foodnamdo.dto.PageResponseDTO;
//import org.zerock.foodnamdo.dto.UserDTO;
//
//@SpringBootTest
//@Log4j2
//public class UserManagementServiceTests {
//
//    @Autowired
//    private UserManagementService userManagementService;
//
////    @Test
////    public void testRegister() {
////        log.info(userManagementService.getClass().getName());
////
////        UserDTO userDTO = UserDTO.builder()
////                .name("김영원")
////                .nickname("김영원원")
////                .phone("010-8888-8888")
////                .accountId("id5")
////                .password("pwd5")
////                .build();
////
////        Long userId = userManagementService.signUp(userDTO);
////
////        log.info("userId: " + userId);
////    }
////
////    @Test
////    public void modify() {
////        UserDTO userDTO = UserDTO.builder()
////                .userId(1L)
////                .nickname("update_belljin")
////                .build();
////
////        userManagementService.modify(userDTO);
////    }
//
////    @Test
////    public void testList() {
////
////        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
////                .type("n")
////                .keyword("b")
////                .page(1)
////                .size(10)
////                .build();
////
////        PageResponseDTO<UserDTO> responseDTO = userManagementService.list(pageRequestDTO);
////
////        log.info(responseDTO);
////    }
//}
