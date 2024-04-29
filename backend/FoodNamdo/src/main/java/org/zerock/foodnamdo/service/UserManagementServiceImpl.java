package org.zerock.foodnamdo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.zerock.foodnamdo.domain.User;
import org.zerock.foodnamdo.dto.UserDTO;
import org.zerock.foodnamdo.repository.UserManagementRepository;

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

    public User findIdByName(String name, String phone) {
        User user = userManagementRepository.findByNameAndPhone(name, phone);
        if (user != null) {
            return user;
        }
        return null;
    }

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
