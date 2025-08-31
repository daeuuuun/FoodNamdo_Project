package org.zerock.foodnamdo.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.foodnamdo.domain.UserEntity;
import org.zerock.foodnamdo.baseDTO.APIUserDTO;
import org.zerock.foodnamdo.repository.APIUserRepository;
import org.zerock.foodnamdo.repository.UserManagementRepository;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class APIUserDetailsService implements UserDetailsService {
    private final UserManagementRepository userManagementRepository;

    @Override
//    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {

//        Optional<UserEntity> result = userManagementRepository.findByAccountId(accountId);
//        Optional<UserEntity> result = userManagementRepository.findById(Long.parseLong(userId));
//        log.info("Loading user by userId: {}", userId);

//        UserEntity userEntity = result.orElseThrow(() -> new UsernameNotFoundException("Cannot find accountId"));
//        UserEntity userEntity = result.orElseThrow(() -> new UsernameNotFoundException("Cannot find userId"));

//        Optional<UserEntity> result = userManagementRepository.findById(Long.parseLong(userId));
//        if (result.isEmpty()) {
//            log.warn("User not found with userId: {}", userId);
//            throw new UsernameNotFoundException("Cannot find userId");
//        }

        Optional<UserEntity> result = userManagementRepository.findByAccountId(accountId);
        if (result.isEmpty()) {
            log.warn("User not found with accountId: {}", accountId);
            throw new UsernameNotFoundException("Cannot find accountId");
        }

        UserEntity userEntity = result.get();
        log.info("Found user: {}", userEntity);


        log.info("APIUserDetailsService apiUser----------------------------------");
        APIUserDTO dto = new APIUserDTO(
                userEntity.getUserId(),
                userEntity.getAccountId(),
                userEntity.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        log.info("Returning UserDetails: {}", dto);
        return dto;
    }
}
