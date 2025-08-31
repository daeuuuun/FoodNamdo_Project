package org.zerock.foodnamdo.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.zerock.foodnamdo.domain.RstrEntity;
import org.zerock.foodnamdo.domain.UserEntity;

@Repository
public interface MainSystemRepositoryUser extends JpaRepository<UserEntity, Long> {



    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.lastVisit = :rstrId WHERE u.userId = :userId")
    void updateLastVisit(@Param("userId") Long userId, @Param("rstrId") Long rstrId);
//    UserEntity findByUserId(Long rstrId);
}
