package org.zerock.foodnamdo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.zerock.foodnamdo.domain.User;
import org.zerock.foodnamdo.repository.search.UserSearch;

@Repository
public interface UserManagementRepository extends JpaRepository<User, Long>, UserSearch {

    @Query(value = "select now()", nativeQuery = true)
    String getTime();

//    User findIdByName(String name);;

    User findByNameAndPhone(String name, String phone);
}
