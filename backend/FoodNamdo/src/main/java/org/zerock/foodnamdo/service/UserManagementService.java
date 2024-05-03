package org.zerock.foodnamdo.service;

import org.springframework.stereotype.Service;
import org.zerock.foodnamdo.domain.User;
import org.zerock.foodnamdo.dto.UserDTO;

//@Service
public interface UserManagementService {

    Long signUp(UserDTO userDTO);

//    UserDTO readOne(Long userId);

//    void modify(UserDTO userDTO);

    void deleteUser(Long userId);

    User findAccountIdByNameAndPhone(String name, String phone);
    User findUserByAccountIdAndNameAndPhone(String accountId, String name, String phone);

    User findUserByPhone(String phone);

    String generateVerificationCode();

//    PageResponseDTO<UserDTO> list(PageRequestDTO pageRequestDTO);
}
