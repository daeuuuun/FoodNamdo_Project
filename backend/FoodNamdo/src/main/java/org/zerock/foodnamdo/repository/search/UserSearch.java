package org.zerock.foodnamdo.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.foodnamdo.domain.UserEntity;

public interface UserSearch {
    Page<UserEntity> search1(Pageable pageable);

    Page<UserEntity> searchAll(String[] types, String keyword, Pageable pageable);
}
