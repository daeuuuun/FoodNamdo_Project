package org.zerock.foodnamdo.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.foodnamdo.domain.User;

public interface UserSearch {
    Page<User> search1(Pageable pageable);

    Page<User> searchAll(String[] types, String keyword, Pageable pageable);
}
