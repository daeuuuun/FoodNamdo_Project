package org.zerock.foodnamdo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserBadgeEntity is a Querydsl query type for UserBadgeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserBadgeEntity extends EntityPathBase<UserBadgeEntity> {

    private static final long serialVersionUID = 1734614956L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserBadgeEntity userBadgeEntity = new QUserBadgeEntity("userBadgeEntity");

    public final DateTimePath<java.time.LocalDateTime> badgeDate = createDateTime("badgeDate", java.time.LocalDateTime.class);

    public final QBadgeEntity badgeEntity;

    public final NumberPath<Long> userBadgeId = createNumber("userBadgeId", Long.class);

    public final QUserEntity userEntity;

    public QUserBadgeEntity(String variable) {
        this(UserBadgeEntity.class, forVariable(variable), INITS);
    }

    public QUserBadgeEntity(Path<? extends UserBadgeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserBadgeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserBadgeEntity(PathMetadata metadata, PathInits inits) {
        this(UserBadgeEntity.class, metadata, inits);
    }

    public QUserBadgeEntity(Class<? extends UserBadgeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.badgeEntity = inits.isInitialized("badgeEntity") ? new QBadgeEntity(forProperty("badgeEntity")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new QUserEntity(forProperty("userEntity")) : null;
    }

}

