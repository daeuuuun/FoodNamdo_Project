package org.zerock.foodnamdo.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.foodnamdo.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class UserManagementReposotoryTests {

    @Autowired
    private UserManagementRepository userManagementRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            User user = User.builder()
                    .name("name1" + i)
                    .nickname("nickname1" + i)
                    .phone("010-0111-0222" + i)
                    .accountId("id1" + i)
                    .password("pwd1" + i)
                    .build();
            User result = userManagementRepository.save(user);
            log.info("userId: " + result.getUserId());
        });
    }

    @Test
    public void testSelect() {
        Long userId = 3L;

        Optional<User> result = userManagementRepository.findById(userId);

        User user = result.orElseThrow();

        log.info(user);
    }

    @Test
    public void testUpdate() {
        Long userId = 1L;

        Optional<User> result = userManagementRepository.findById(userId);

        User user = result.orElseThrow();

        user.changePassword("pwd11");

        userManagementRepository.save(user);
    }

    @Test
    public void testDelete() {
        Long userId = 1L;

        userManagementRepository.deleteById(userId);
    }

    @Test
    public void testPaging() {
        Pageable pageable = PageRequest.of(0,10, Sort.by("userId").descending());

        Page<User> result = userManagementRepository.findAll(pageable);


        log.info("total count: "+result.getTotalElements());
        log.info( "total pages:" +result.getTotalPages());
        log.info("page number: "+result.getNumber());
        log.info("page size: "+result.getSize());

        List<User> todoList = result.getContent();

        todoList.forEach(user -> log.info(user));
    }

    @Test
    public void testSearch1() {
        Pageable pageable = PageRequest.of(1, 10, Sort.by("userId").descending());

        userManagementRepository.search1(pageable);
    }

    @Test
    public void testSearchAll() {
        String[] types = {"t", "c", "w"};

        String keyword = "1";

        Pageable pageable = PageRequest.of(0, 10, Sort.by("userId").descending());

        Page<User> result = userManagementRepository.searchAll(types, keyword, pageable);
    }

    @Test
    public void testSearchAll2() {
        String[] types = {"t", "c", "w"};

        String keyword = "j";

        Pageable pageable = PageRequest.of(0, 10, Sort.by("userId").descending());

        Page<User> result = userManagementRepository.searchAll(types, keyword, pageable);

        log.info(result.getTotalPages());

        log.info(result.getSize());

        log.info(result.getNumber());

        log.info(result.hasPrevious() + ": " + result.hasNext());

        result.getContent().forEach(user -> log.info(user));
    }
}



