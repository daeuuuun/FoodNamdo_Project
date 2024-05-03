package org.zerock.foodnamdo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.zerock.foodnamdo.domain.User;
import org.zerock.foodnamdo.dto.UserDTO;
import org.zerock.foodnamdo.repository.UserManagementRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class UserManagementServiceImpl implements UserManagementService{

    private final ModelMapper modelMapper;

    private final UserManagementRepository userManagementRepository;



    @Override
    public Long signUp(UserDTO userDTO) {

        User user = modelMapper.map(userDTO, User.class);

        Long userId = userManagementRepository.save(user).getUserId();

        return userId;
    }

    public User findAccountIdByNameAndPhone(String name, String phone) {
        // 사용자 정보를 DB에서 조회
        User user = userManagementRepository.findAccountIdByNameAndPhone(name, phone);
        System.out.println(user);
        if (user != null) {
            return user;
        }
        return null;
    }

    public User findUserByAccountIdAndNameAndPhone(String accountId, String name, String phone) {
        // 사용자 정보를 DB에서 조회
        User user = userManagementRepository.findUserByAccountIdAndNameAndPhone(accountId, name, phone);
        System.out.println(user);
        if (user != null) {
            return user;
        }
        return null;
    }

    public User findUserByPhone(String phone) {
        // 사용자 정보를 DB에서 조회
        User user = userManagementRepository.findUserByPhone(phone);
        System.out.println(user);
        if (user != null) {
            return user;
        }
        return null;
    }

//        Optional<User> userOptional = Optional.ofNullable(userManagementRepository.findAccountIdByNameAndPhone(name, phone));
//        if (userOptional.isPresent()) {
//            // 인증번호 생성 및 저장
//            String verificationCode = generateVerificationCode();
//            verificationCodes.put(phone, verificationCode);
//
//            // SMS 서비스를 사용하여 인증번호 전송
//            twilioService.sendSms(phone, "Your verification code is: " + verificationCode);
//            return user;
//        }

//    public Optional<String> verifyUser(String phone, String verificationCode) {
//        // 전송된 인증번호와 일치하는지 확인
//        if (verificationCodes.containsKey(phone) && verificationCodes.get(phone).equals(verificationCode)) {
//            // 일치할 경우 사용자 ID 반환
//            Optional<User> userOptional = userManagementRepository.findByPhone(phone);
//            if (userOptional.isPresent()) {
//                return Optional.of(userOptional.get().getAccountId());
//            }
//        }
//        return Optional.empty();
//    }

    public String generateVerificationCode() {
        // 인증번호 생성 로직
        String code = String.valueOf(new Random().nextInt(899999) + 100000); // 예시: 6자리 무작위 숫자
        System.out.println(code);
        return code;
    }
//    public User findUserByNameAndPhone(String name, String phone) {
//        User user = userManagementRepository.findUserByNameAndPhone(name, phone);
//        if (user != null) {
//            return user;
//        }
//        return null;
//    }

//    @Override
//    public UserDTO readOne(Long userId) {
//        Optional<User> result = userManagementRepository.findById(userId);
//
//        User user = result.orElseThrow();
//
//        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
//
//        return userDTO;
//    }
//
//    @Override
//    public void modify(UserDTO userDTO) {
//
//        Optional<User> result = userManagementRepository.findById(userDTO.getUserId());
//
//        User user = result.orElseThrow();
//
//        user.changeNickname(userDTO.getNickname());
//
//        userManagementRepository.save(user);
//    }

    @Override
    public void deleteUser(Long userId) {
        userManagementRepository.deleteById(userId);
    }

//    @Override
//    public PageResponseDTO<UserDTO> list(PageRequestDTO pageRequestDTO) {
//
//        String[] types = pageRequestDTO.getTypes();
//        String keyword = pageRequestDTO.getKeyword();
//        Pageable pageable = pageRequestDTO.getPageable("userId");
//
//        Page<User> result = userManagementRepository.searchAll(types, keyword, pageable);
//
//        List<UserDTO> dtoList = result.getContent().stream()
//                .map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
//
//        return PageResponseDTO.<UserDTO>withAll()
//                .pageRequestDTO(pageRequestDTO)
//                .dtoList(dtoList)
//                .total((int)result.getTotalElements())
//                .build();
//    }
}
