package org.zerock.foodnamdo.service;

import org.zerock.foodnamdo.domain.UserEntity;
import org.zerock.foodnamdo.dto.SignUpDTO;
import org.zerock.foodnamdo.dto.UserDTO;

//@Service
public interface UserManagementService {

    Long signUp(SignUpDTO signUpDTO);

//    UserDTO readOne(Long userId);

//    void modify(UserDTO userDTO);

    void deleteUser(Long userId);

    UserEntity findUserByAccountId(String accountId);


    UserEntity findUserByNickname(String nickname);

    UserEntity findAccountIdByNameAndPhone(String name, String phone);
    UserEntity findUserByAccountIdAndNameAndPhone(String accountId, String name, String phone);

    UserEntity findUserByPhone(String phone);

    String generateVerificationCode();

//    PageResponseDTO<UserDTO> list(PageRequestDTO pageRequestDTO);
}
