package org.zerock.foodnamdo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserEntity is a Querydsl query type for UserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEntity extends EntityPathBase<UserEntity> {

    private static final long serialVersionUID = -577710627L;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final StringPath accountId = createString("accountId");

    public final ListPath<FavoriteEntity, QFavoriteEntity> favorites = this.<FavoriteEntity, QFavoriteEntity>createList("favorites", FavoriteEntity.class, QFavoriteEntity.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final ListPath<UserPreferenceEntity, QUserPreferenceEntity> preferences = this.<UserPreferenceEntity, QUserPreferenceEntity>createList("preferences", UserPreferenceEntity.class, QUserPreferenceEntity.class, PathInits.DIRECT2);

    public final ListPath<ReactionEntity, QReactionEntity> reactions = this.<ReactionEntity, QReactionEntity>createList("reactions", ReactionEntity.class, QReactionEntity.class, PathInits.DIRECT2);

    public final ListPath<ReviewEntity, QReviewEntity> reviews = this.<ReviewEntity, QReviewEntity>createList("reviews", ReviewEntity.class, QReviewEntity.class, PathInits.DIRECT2);

    public final ListPath<UserBadgeEntity, QUserBadgeEntity> userBadges = this.<UserBadgeEntity, QUserBadgeEntity>createList("userBadges", UserBadgeEntity.class, QUserBadgeEntity.class, PathInits.DIRECT2);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QUserEntity(String variable) {
        super(UserEntity.class, forVariable(variable));
    }

    public QUserEntity(Path<? extends UserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserEntity(PathMetadata metadata) {
        super(UserEntity.class, metadata);
    }

}

