package org.zerock.foodnamdo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.zerock.foodnamdo.baseDTO.UserDTO;
import org.zerock.foodnamdo.domain.UserEntity;
import org.zerock.foodnamdo.customDTO.SignUpDTO;

//@Service
public interface UserManagementService {

    Long signUp(SignUpDTO signUpDTO);

//    UserDTO readOne(Long userId);

    void modify(UserDTO userDTO);

    void deleteUser(Long userId);

    UserEntity findUserByAccountId(String accountId);



    UserEntity findUserByNickname(String nickname);

    UserEntity findAccountIdByNameAndPhone(String name, String phone);
    UserEntity findUserByAccountIdAndNameAndPhone(String accountId, String name, String phone);

    UserEntity findUserByPhone(String phone);

    UserEntity findByUserId(Long userId);

    String generateVerificationCode();

//    UserDetails findUserByUserId(String userId);

//    PageResponseDTO<UserDTO> list(PageRequestDTO pageRequestDTO);
}
