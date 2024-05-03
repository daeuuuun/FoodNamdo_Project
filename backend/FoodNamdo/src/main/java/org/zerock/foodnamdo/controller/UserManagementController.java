package org.zerock.foodnamdo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.foodnamdo.dto.UserDTO;
import org.zerock.foodnamdo.service.TwilioService;
import org.zerock.foodnamdo.service.UserManagementService;
import org.zerock.foodnamdo.service.CoolsmsService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/usermanagement")
@Tag(name = "UserManagementAPI", description = "회원가입, 로그인, 로그아웃, 아이디찾기, 비밀번호 찾기, 회원 탈퇴")
@Log4j2
@RequiredArgsConstructor
public class UserManagementController {

    private final UserManagementService userManagementService;

    private Map<String, String> verificationCodes = new HashMap<>();

//    @GetMapping("/list")
//    public void list(PageRequestDTO pageRequestDTO, Model model) {
//        PageResponseDTO<UserDTO> responseDTO = userManagementService.list(pageRequestDTO);
//
//        log.info(responseDTO);
//
//        model.addAttribute("responseDTO", responseDTO);
//    }

//    @GetMapping("/signUp")
//    public void registerGET() {
//
//    }

    @Operation(summary = "회원가입")
    @PostMapping("/signUp")
    public String signUp(@RequestParam("UserDTO") @Valid UserDTO userDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("user signUp........");

        if (bindingResult.hasErrors()) {
            log.info("has errors.....");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return redirectAttributes.toString();
        }

        log.info(userDTO);

        Long userId = userManagementService.signUp(userDTO);

        redirectAttributes.addFlashAttribute("result", userId);

        return redirectAttributes.toString();
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public void login() {

    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public void logout() {

    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        log.info("login post..........");
//
//        String mid = req.getParameter("mid");
//        String mpw = req.getParameter("mpw");
//
//        String auto = req.getParameter("auto");
//        boolean rememberMe = auto != null && auto.equals("on");
//
//        try {
//            UserDTO userDTO = userManagementService.INSTANCE.login(mid, mpw);
//            if (rememberMe) {
//                String uuid = UUID.randomUUID().toString();
//                userManagementService.INSTANCE.updateUuid(mid, uuid);
//                userDTO.setUuid(uuid);
//
//                Cookie rememberCookie = new Cookie("remember-me", uuid);
//                rememberCookie.setMaxAge(60 * 60 * 24);
//                rememberCookie.setPath("/");
//                resp.addCookie(rememberCookie);
//            }
//            HttpSession session = req.getSession();
//            session.setAttribute("loginInfo", userDTO);
//            resp.sendRedirect("/todo/list");
//        } catch (Exception e) {
//            resp.sendRedirect("/login?result=error");
//        }
//    }

    @Operation(summary = "이름, 전화번호를 이용해 아이디 찾기")
    @PostMapping("/findAccountIdByNameAndPhone")
    public UserDTO findAccountIdByNameAndPhone(
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("code") String code) {
        String formatPhone = phone.substring(0, 3) + "-" + phone.substring(3, 7) + "-" + phone.substring(7);
        log.info("findAccountIdByNameAndPhone...." + name + formatPhone);
        String savedCode = verificationCodes.get(formatPhone);

        if (savedCode == null || !savedCode.equals(code)) {
            UserDTO userDTO = UserDTO.fromUser(userManagementService.findAccountIdByNameAndPhone(name, formatPhone));
            System.out.println(userManagementService.findAccountIdByNameAndPhone(name, formatPhone));
            if(userDTO == null) return null;
            return userDTO;
        } else {
            return null;
        }
    }

    @Operation(summary = "아이디, 이름, 전화번호를 이용해 비밀번호 찾기")
    @PostMapping("/findPasswordByAccountIdAndNameAndPhone")
    public UserDTO findPasswordByAccountIdAndNameAndPhone(
            @RequestParam("accountId") String accountId,
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("code") String code) {
        String formatPhone = phone.substring(0, 3) + "-" + phone.substring(3, 7) + "-" + phone.substring(7);
        log.info("findAccountIdByNameAndPhone...." + name + formatPhone);
        String savedCode = verificationCodes.get(formatPhone);

        if (savedCode == null || !savedCode.equals(code)) {
            UserDTO userDTO = UserDTO.fromUser(userManagementService.findUserByAccountIdAndNameAndPhone(accountId, name, formatPhone));
            System.out.println(userManagementService.findUserByAccountIdAndNameAndPhone(accountId, name, formatPhone));
            if(userDTO == null) return null;
            return userDTO;
        } else {
            return null;
        }
    }

//        if (userDTO != null) {
//            String verificationCode = userManagementService.generateVerificationCode();
//            verificationCodes.put(formatPhone, verificationCode);
////            coolsmsService.sendSMS(userDTO.getPhone(),verificationCode);
//            //        UserDTO userDTO = UserDTO.fromUser(userManagementService.findUserByPhone(formatPhone));
//            return true;
//        } else {
//            return false;
//        }

    @Operation(summary = "인증번호 요청")
    @PostMapping("/verify")
    public boolean verify(@RequestParam("phone") String phone) {
        String formatPhone = phone.substring(0, 3) + "-" + phone.substring(3, 7) + "-" + phone.substring(7);
        String verificationCode = userManagementService.generateVerificationCode();
        verificationCodes.put(formatPhone, verificationCode);
        CoolsmsService coolsmsService = new CoolsmsService();
        UserDTO userDTO = UserDTO.fromUser(userManagementService.findUserByPhone(formatPhone));

        if (userDTO != null) {
            try {
                coolsmsService.sendSMS(formatPhone, verificationCode);
                return true;
            } catch (Exception e) {
                return false;
            }
        }else return false;
    }

//    @GetMapping("/read")
//    public void read(Long userId, PageRequestDTO pageRequestDTO, Model model) {
//
//        UserDTO userDTO = userManagementService.readOne(userId);
//
//        log.info(userDTO);
//
//        model.addAttribute("userId", userDTO);
//    }

//    @GetMapping({"/read", "/modify"})
//    public void read(Long userId, PageRequestDTO pageRequestDTO, Model model) {
//
//        UserDTO userDTO = userManagementService.readOne(userId);
//
//        log.info(userDTO);
//
//        model.addAttribute("userId", userDTO);
//    }

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

    @Operation(summary = "회원삭제")
    @PostMapping("/deleteUser")
    public String remove(@RequestParam("userId") Long userId, RedirectAttributes redirectAttributes) {
        log.info("delete user.." + userId);

        userManagementService.deleteUser(userId);

        redirectAttributes.addFlashAttribute("result", "deleted");

        return redirectAttributes.toString();
    }


}
