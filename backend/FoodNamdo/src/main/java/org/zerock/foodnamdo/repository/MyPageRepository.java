package org.zerock.foodnamdo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.foodnamdo.domain.RstrEntity;
import org.zerock.foodnamdo.domain.UserEntity;
import org.zerock.foodnamdo.repository.search.UserSearch;

import java.util.List;

@Repository
public interface MyPageRepository extends JpaRepository<UserEntity, Long>, UserSearch {
//public interface MyPageRepository extends JpaRepository<UserEntity, Long> {
}
