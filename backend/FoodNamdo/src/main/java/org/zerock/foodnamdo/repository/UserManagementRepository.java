package org.zerock.foodnamdo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.zerock.foodnamdo.domain.UserEntity;
import org.zerock.foodnamdo.repository.search.UserSearch;

import java.util.Optional;

@Repository
public interface UserManagementRepository extends JpaRepository<UserEntity, Long>, UserSearch {
//public interface UserManagementRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "select now()", nativeQuery = true)
    String getTime();

//    User findIdByName(String name);;

    UserEntity findAccountIdByNameAndPhone(String name, String phone);

    UserEntity findUserByAccountIdAndNameAndPhone(String accountId, String name, String phone);

    UserEntity findUserByPhone(String phone);

    UserEntity findUserByName(String name);

    UserEntity findUserByAccountId(String accountId);

    UserEntity findUserByNickname(String nickname);

    Optional<UserEntity> findByAccountId(String accountId);

    UserEntity findUserByUserId(Long userId);

    UserEntity findByUserId(Long userId);
}
