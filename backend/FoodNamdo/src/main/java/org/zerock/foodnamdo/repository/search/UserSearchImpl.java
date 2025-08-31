package org.zerock.foodnamdo.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.foodnamdo.domain.QUserEntity;
import org.zerock.foodnamdo.domain.UserEntity;

import java.util.List;

public class UserSearchImpl extends QuerydslRepositorySupport implements UserSearch {
    public UserSearchImpl(){
        super(UserEntity.class);
    }

    @Override
    public Page<UserEntity> search1(Pageable pageable){
        QUserEntity userEntity = QUserEntity.userEntity;

        JPQLQuery<UserEntity> query = from(userEntity);

        query.where(userEntity.nickname.contains("belljin"));

        this.getQuerydsl().applyPagination(pageable, query);

        List<UserEntity> list = query.fetch();

        long count = query.fetchCount();

        return null;
    }

    @Override
    public Page<UserEntity> searchAll(String[] types, String keyword, Pageable pageable) {
        QUserEntity userEntity = QUserEntity.userEntity;
        JPQLQuery<UserEntity> query = from(userEntity);

        if( (types != null && types.length > 0) && keyword != null ) {

            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for(String type: types) {
                switch (type){
                    case "t":
                        booleanBuilder.or(userEntity.name.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(userEntity.phone.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(userEntity.accountId.contains(keyword));
                        break;
                }
            }
            query.where(booleanBuilder);
        }
        query.where(userEntity.userId.gt(0L));

        this.getQuerydsl().applyPagination(pageable, query);

        List<UserEntity> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }
}
