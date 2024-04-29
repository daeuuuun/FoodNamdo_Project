package org.zerock.foodnamdo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.foodnamdo.domain.User;
import org.zerock.foodnamdo.repository.search.UserSearch;

public interface MyPageRepository extends JpaRepository<User, Long>, UserSearch {
}
