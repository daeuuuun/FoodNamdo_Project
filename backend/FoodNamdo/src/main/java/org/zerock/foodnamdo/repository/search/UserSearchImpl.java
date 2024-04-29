package org.zerock.foodnamdo.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.foodnamdo.domain.QUser;
import org.zerock.foodnamdo.domain.User;

import java.util.List;

public class UserSearchImpl extends QuerydslRepositorySupport implements UserSearch {
    public UserSearchImpl(){
        super(User.class);
    }

    @Override
    public Page<User> search1(Pageable pageable){
        QUser user = QUser.user;

        JPQLQuery<User> query = from(user);

        query.where(user.nickname.contains("belljin"));

        this.getQuerydsl().applyPagination(pageable, query);

        List<User> list = query.fetch();

        long count = query.fetchCount();

        return null;
    }

    @Override
    public Page<User> searchAll(String[] types, String keyword, Pageable pageable) {
        QUser user = QUser.user;
        JPQLQuery<User> query = from(user);

        if( (types != null && types.length > 0) && keyword != null ) {

            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for(String type: types) {
                switch (type){
                    case "t":
                        booleanBuilder.or(user.name.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(user.phone.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(user.accountId.contains(keyword));
                        break;
                }
            }
            query.where(booleanBuilder);
        }
        query.where(user.userId.gt(0L));

        this.getQuerydsl().applyPagination(pageable, query);

        List<User> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }
}
