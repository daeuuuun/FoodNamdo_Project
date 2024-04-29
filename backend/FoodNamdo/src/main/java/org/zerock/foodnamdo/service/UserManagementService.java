package org.zerock.foodnamdo.service;

import org.springframework.stereotype.Service;
import org.zerock.foodnamdo.domain.User;
import org.zerock.foodnamdo.dto.UserDTO;

@Service
public interface UserManagementService {

    Long signUp(UserDTO userDTO);

//    UserDTO readOne(Long userId);

//    void modify(UserDTO userDTO);

    void deleteUser(Long userId);

    User findIdByName(String name, String phone);

//    PageResponseDTO<UserDTO> list(PageRequestDTO pageRequestDTO);
}
