package org.zerock.foodnamdo.security;

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

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class APIUserDetailsService implements UserDetailsService {
    private final APIUserRepository apiUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> result = apiUserRepository.findById(username);

        UserEntity UserEntity = result.orElseThrow(() -> new UsernameNotFoundException("Cannot find mid"));

        log.info("APIUserDetailsService apiUser----------------------------------");
        APIUserDTO dto = new APIUserDTO(
                UserEntity.getAccountId(),
                UserEntity.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        log.info(dto);
        return dto;
    }
}
