package org.zerock.foodnamdo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.foodnamdo.domain.UserEntity;
import org.zerock.foodnamdo.customDTO.SignUpDTO;
import org.zerock.foodnamdo.service.UserManagementService;
import org.zerock.foodnamdo.service.CoolsmsService;
import org.zerock.foodnamdo.util.JWTUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/usermanagement")
@Tag(name = "UserManagementAPI", description = "회원가입, 로그인, 로그아웃, 아이디찾기, 비밀번호 찾기, 회원 탈퇴")
@Log4j2
@RequiredArgsConstructor
public class UserManagementController {

//    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;


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


//    @Operation(summary = "회원가입")
//    @PostMapping("/signUp")
//    public String signUp(@RequestParam("SignUpDTO") @Valid SignUpDTO signUpDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
//        log.info("user signUp........");
//
//        if (bindingResult.hasErrors()) {
//            log.info("has errors.....");
//            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
//
//            return redirectAttributes.toString();
//        }
//
//        log.info(signUpDTO);
//
//        Long userId = userManagementService.signUp(signUpDTO);
//
//        redirectAttributes.addFlashAttribute("result", userId);
//
//        return redirectAttributes.toString();
//    }

    @Operation(summary = "아이디중복체크(아이디가 존재할 경우 true, 아닐 경우 false)")
    @GetMapping(value = "/IdDuplication", produces = "application/json")
    public boolean IdDuplication(@RequestParam("account_id")String accountId){
        UserEntity userEntity = userManagementService.findUserByAccountId(accountId);
        return userEntity != null;
    }

    @Operation(summary = "닉네임중복체크(닉네임이 존재할 경우 true, 아닐 경우 false)")
    @GetMapping(value = "/NicknameDuplication", produces = "application/json")
    public boolean NicknameDuplication(@RequestParam("nickname")String nickname){
        UserEntity userEntity = userManagementService.findUserByNickname(nickname);
        return userEntity != null;
    }

    @Operation(summary = "회원가입")
    @PostMapping("/signUp")
    public String signUp(
            @RequestParam("name")String name,
            @RequestParam("nickname") String nickname,
            @RequestParam("phone") String phone,
            @RequestParam("accountId") String accountId,
            @RequestParam("password") String password
//            BindingResult bindingResult, RedirectAttributes redirectAttributes
    ) {
//        log.info("user signUp........");
//        if (bindingResult.hasErrors()) {
//            log.info("has errors.....");
//            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
//
//            return redirectAttributes.toString();
//        }

        SignUpDTO signUpDTO = SignUpDTO.builder()
                .name(name)
                .nickname(nickname)
                .phone(phone)
                .accountId(accountId)
                .password(password)
                .build();

        log.info(signUpDTO);

        Long userId = userManagementService.signUp(signUpDTO);

        String result = "id = " + userId + name + "님, 회원가입이 완료되었습니다";

        return result;
    }

//    @Operation(summary = "로그인")
//    @PostMapping("/logIn")
//    public ResponseEntity<LoginResponseDTO> logIn(@RequestBody LoginRequestDTO loginRequest) {
//        // 사용자 인증
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//
//        // 인증이 성공하면 JWT 토큰 생성
//        String accessToken = jwtUtil.generateToken(authentication.getName(), 1);
//
////        String accessToken = jwtUtil.generateToken(authentication.getName(), 1);
//
//        // 응답으로 JWT 토큰 반환
//        return ResponseEntity.ok(new LoginResponseDTO(accessToken));
//    }

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
    @PostMapping(value = "/findAccountIdByNameAndPhone", produces = "application/json")
    @ResponseBody
//    public UserDTO findAccountIdByNameAndPhone(
    public ResponseEntity<Object> findAccountIdByNameAndPhone(
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("code") String code
    ) {
        String formatPhone = phone.substring(0, 3) + "-" + phone.substring(3, 7) + "-" + phone.substring(7);
        log.info("findAccountIdByNameAndPhone...." + name + formatPhone + code);
        String savedCode = verificationCodes.get(formatPhone);
        log.info("savedCode....." + savedCode);
        CoolsmsService coolsmsService = new CoolsmsService();
        int IdOrPwd = 0;

        if (savedCode != null && savedCode.equals(code)) {
            UserEntity userEntity = userManagementService.findAccountIdByNameAndPhone(name, formatPhone);
            if (userEntity != null) {
                Map<String, String> response = new HashMap<>();
                response.put("accountId", "sendId");
//                response.put("accountId", userEntity.getAccountId());
                try{
                    coolsmsService.sendIdOrPwd(formatPhone, userEntity.getAccountId(), IdOrPwd);
                } catch (Exception e) {
                return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok(response);
            } else {
                log.info("ResponseEntity.notFound().build();");
                return ResponseEntity.notFound().build();
            }
        } else {
            log.info("ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
//        if (savedCode != null && savedCode.equals(code)) {
//            UserDTO userDTO = UserDTO.fromUser(userManagementService.findAccountIdByNameAndPhone(name, formatPhone));
//            System.out.println(userManagementService.findAccountIdByNameAndPhone(name, formatPhone));
//            if(userDTO == null) return null;
//            return userDTO;
//        } else {
//            return null;
//        }

    @Operation(summary = "아이디, 이름, 전화번호를 이용해 비밀번호 찾기")
    @PostMapping(value = "/findPasswordByAccountIdAndNameAndPhone", produces = "application/json")
//    public UserDTO findPasswordByAccountIdAndNameAndPhone(
    public ResponseEntity<Object> findPasswordByAccountIdAndNameAndPhone(
            @RequestParam("accountId") String accountId,
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("code") String code) {
        String formatPhone = phone.substring(0, 3) + "-" + phone.substring(3, 7) + "-" + phone.substring(7);
        log.info("findAccountIdByNameAndPhone...." + name + formatPhone + code);
        String savedCode = verificationCodes.get(formatPhone);
        log.info("savedCode....." + savedCode);
        CoolsmsService coolsmsService = new CoolsmsService();
        int IdOrPwd = 1;

        if (savedCode != null && savedCode.equals(code)) {
            UserEntity userEntity = userManagementService.findUserByAccountIdAndNameAndPhone(accountId, name, formatPhone);
            if (userEntity != null) {
                Map<String, String> response = new HashMap<>();
                response.put("password", "sendPassword");
//                response.put("password", userEntity.getPassword());
                try{
                    coolsmsService.sendIdOrPwd(formatPhone, userEntity.getPassword(), IdOrPwd);
                } catch (Exception e) {
                    return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok(response);
            } else {
                log.info("ResponseEntity.notFound().build();");
                return ResponseEntity.notFound().build();
            }
        } else {
            log.info("ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
//        log.info(savedCode);

//        if (savedCode != null && savedCode.equals(code)) {
//            UserDTO userDTO = UserDTO.fromUser(userManagementService.findUserByAccountIdAndNameAndPhone(accountId, name, formatPhone));
//            System.out.println(userManagementService.findUserByAccountIdAndNameAndPhone(accountId, name, formatPhone));
//            if(userDTO == null) return null;
//            return userDTO;
//        } else {
//            return null;
//        }
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
    @PostMapping(value = "/verify", produces = "application/json")
    public boolean verify(@RequestParam("phone") String phone) {
        String formatPhone = phone.substring(0, 3) + "-" + phone.substring(3, 7) + "-" + phone.substring(7);
        UserEntity userEntity = userManagementService.findUserByPhone(formatPhone);
        CoolsmsService coolsmsService = new CoolsmsService();

        if (userEntity != null) {
            try {
                String verificationCode = userManagementService.generateVerificationCode();
                verificationCodes.put(formatPhone, verificationCode);
                log.info(verificationCodes);
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
    public String remove(@RequestParam("user_id") Long userId, RedirectAttributes redirectAttributes) {
        log.info("delete user.." + userId);

        userManagementService.deleteUser(userId);

        redirectAttributes.addFlashAttribute("result", "deleted");

        return redirectAttributes.toString();
    }


}
