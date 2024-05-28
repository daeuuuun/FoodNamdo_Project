package org.zerock.foodnamdo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.zerock.foodnamdo.domain.RstrEntity;
import org.zerock.foodnamdo.domain.UserEntity;

@Repository
public interface MainSystemRepositoryUser extends JpaRepository<UserEntity, Long> {


//    UserEntity findByUserId(Long rstrId);
}
