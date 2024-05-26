package org.zerock.foodnamdo.controller;

import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.zerock.foodnamdo.customDTO.*;
import org.zerock.foodnamdo.domain.UserEntity;
import org.zerock.foodnamdo.security.service.EncryptionService;
import org.zerock.foodnamdo.security.service.TokenBlacklistService;
import org.zerock.foodnamdo.service.UserManagementService;
import org.zerock.foodnamdo.service.CoolsmsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.foodnamdo.util.AESUtil;
import org.zerock.foodnamdo.util.JWTUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/usermanagement")
@Tag(name = "UserManagementAPI", description = "회원가입, 로그인, 로그아웃, 아이디찾기, 비밀번호 찾기, 회원 탈퇴")
@Log4j2
@RequiredArgsConstructor
public class UserManagementController {


//    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final UserManagementService userManagementService;
    private final TokenBlacklistService tokenBlacklistService;
    private final PasswordEncoder passwordEncoder;
    private final EncryptionService encryptionService;
    private Map<String, String> verificationCodes = new HashMap<>();

    //    private final AuthenticationManager authenticationManager;
//    private final JwtUtil jwtUtil;
//    private final JWTUtilPrev jwtUtil;


//    private final UserManagementService userManagementService;

//    private Map<String, String> verificationCodes = new HashMap<>();



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
    public ResponseEntity<String> signUp(
            @Valid @RequestBody SignUpDTO signUpDTO
    ) {

        String phone = signUpDTO.getPhone();
        String formatPhone = phone.substring(0, 3) + "-" + phone.substring(3, 7) + "-" + phone.substring(7);
        signUpDTO.setPhone(formatPhone);

//        String encodedPassword = passwordEncoder.encode(signUpDTO.getPassword());
//        signUpDTO.setPassword(encodedPassword);

        try {
            String encryptedPassword = encryptionService.encrypt(signUpDTO.getPassword());
            signUpDTO.setPassword(encryptedPassword);
        } catch (Exception e) {
            log.error("Error encrypting password", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during encryption");
        }

        log.info(signUpDTO);

        userManagementService.signUp(signUpDTO);

        return ResponseEntity.ok("Signup successfully");
    }

    @Operation(summary = "로그인")
    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
//    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getAccountId(),
                            loginRequestDTO.getPassword()
                    )
            );


            String accountId = authentication.getName();
            UserEntity userEntity = userManagementService.findUserByAccountId(accountId);
            Long userId = userEntity.getUserId();

            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", userId);
            claims.put("roles", authentication.getAuthorities());

            String accessToken = jwtUtil.generateAccessToken(claims); // 3 minute for access token
            String refreshToken = jwtUtil.generateRefreshToken(claims); // 7 days for refresh token

            LoginResponseDTO loginResponse = new LoginResponseDTO(accessToken, refreshToken);

            return ResponseEntity.ok(loginResponse);
        } catch (AuthenticationException e) {
            log.error("AuthenticationException: ", e);
            return ResponseEntity.status(401).build();
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseEntity.status(500).build();
        }
    }

    @Operation(summary = "토큰 갱신", security = @SecurityRequirement(name = "jwtAuth"))
    @PostMapping(value = "/refreshToken", produces = "application/json")
//    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, String> request) {
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody AccessRefreshTokenRequestDTO request) {
        String refreshToken = request.getRefreshToken();
//        String refreshToken = request.get("refreshToken");
        try {
            Map<String, Object> claims = jwtUtil.validateToken(refreshToken);
            int userIdint = (int) claims.get("userId");
            Long userId = (long) userIdint;
            log.info(userId);
//            Long userId = userIdstr.t
//            String userId = (String) claims.get("sub");
//            String accountId = (String) claims.get("sub");

            UserEntity userEntity = userManagementService.findByUserId(userId);
//            UserEntity userEntity = userManagementService.findUserByAccountId(accountId);
            if (userEntity == null) {
                throw new UsernameNotFoundException("User not found");
            }

            Map<String, Object> newClaims = new HashMap<>();
            newClaims.put("userId", userEntity.getUserId());
            newClaims.put("roles", claims.get("roles"));

            String newAccessToken = jwtUtil.generateAccessToken(newClaims); // new access token
//            String newAccessToken = jwtUtil.generateToken(newClaims, 1); // 1 day for new access token
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", newAccessToken);
            tokens.put("refreshToken", refreshToken); // Optionally, generate a new refresh token

            return ResponseEntity.ok(tokens);
        } catch (JwtException e) {
            return ResponseEntity.status(401).build();
        }
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody RefreshTokenRequestDTO request) {
//    public ResponseEntity<Void> logout(@RequestBody Map<String, String> request) {
        String refreshToken = request.getRefreshToken();
//        String refreshToken = request.get("refreshToken");
        tokenBlacklistService.blacklistToken(refreshToken);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "이름, 전화번호를 이용해 아이디 찾기")
    @PostMapping(value = "/findAccountIdByNameAndPhone", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> findAccountIdByNameAndPhone(
            @RequestBody findAccountIdDTO request
    ) {
        String formatPhone = request.getPhone().substring(0, 3) + "-" + request.getPhone().substring(3, 7) + "-" + request.getPhone().substring(7);
        log.info("findAccountIdByNameAndPhone...." + request.getName() + formatPhone + request.getCode());
        String savedCode = verificationCodes.get(formatPhone);
        log.info("savedCode....." + savedCode);
        CoolsmsService coolsmsService = new CoolsmsService();
        int IdOrPwd = 0;

        if (savedCode != null && savedCode.equals(request.getCode())) {
            UserEntity userEntity = userManagementService.findAccountIdByNameAndPhone(request.getName(), formatPhone);
            if (userEntity != null) {
                Map<String, String> response = new HashMap<>();
                response.put("accountId", "sendId");
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

    @Operation(summary = "아이디, 이름, 전화번호를 이용해 비밀번호 찾기")
    @PostMapping(value = "/findPasswordByAccountIdAndNameAndPhone", produces = "application/json")
    public ResponseEntity<Object> findPasswordByAccountIdAndNameAndPhone(
            @RequestBody findPasswordDTO request)
    {
        String formatPhone = request.getPhone().substring(0, 3) + "-" + request.getPhone().substring(3, 7) + "-" + request.getPhone().substring(7);
        log.info("findPasswordByAccountIdAndNameAndPhone...." + request.getAccountId() + request.getName() + formatPhone + request.getCode());
        String savedCode = verificationCodes.get(formatPhone);
        log.info("savedCode....." + savedCode);
        CoolsmsService coolsmsService = new CoolsmsService();
        int IdOrPwd = 1;

        if (savedCode != null && savedCode.equals(request.getCode())) {
            UserEntity userEntity = userManagementService.findUserByAccountIdAndNameAndPhone(request.getAccountId(), request.getName(), formatPhone);
            if (userEntity != null) {
                Map<String, String> response = new HashMap<>();
//                response.put("password", "sendPassword");
                try{
                    String decryptedPassword = encryptionService.decrypt(userEntity.getPassword());
                    response.put("password", "sendPassword");
                    coolsmsService.sendIdOrPwd(formatPhone, decryptedPassword, IdOrPwd);
//                    coolsmsService.sendIdOrPwd(formatPhone, userEntity.getPassword(), IdOrPwd);
                } catch (Exception e) {
                    log.error("Error decrypting password", e);
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
    public ResponseEntity<String> remove(@RequestParam("user_id") Long userId) {
        log.info("delete user.." + userId);

        userManagementService.deleteUser(userId);

//        redirectAttributes.addFlashAttribute("result", "deleted");

        return ResponseEntity.ok("delete userId" + userId + " successfully");
    }


}
