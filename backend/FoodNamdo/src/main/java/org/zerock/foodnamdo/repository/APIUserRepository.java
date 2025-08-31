package org.zerock.foodnamdo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.zerock.foodnamdo.domain.APIUser;
import org.zerock.foodnamdo.domain.UserEntity;

public interface APIUserRepository extends JpaRepository<UserEntity, String> {
}
