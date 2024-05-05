package org.zerock.foodnamdo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBadgeEntity is a Querydsl query type for BadgeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBadgeEntity extends EntityPathBase<BadgeEntity> {

    private static final long serialVersionUID = -436842249L;

    public static final QBadgeEntity badgeEntity = new QBadgeEntity("badgeEntity");

    public final StringPath badgeComment = createString("badgeComment");

    public final NumberPath<Long> badgeId = createNumber("badgeId", Long.class);

    public final StringPath badgeName = createString("badgeName");

    public final StringPath badgeUnlock = createString("badgeUnlock");

    public QBadgeEntity(String variable) {
        super(BadgeEntity.class, forVariable(variable));
    }

    public QBadgeEntity(Path<? extends BadgeEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBadgeEntity(PathMetadata metadata) {
        super(BadgeEntity.class, metadata);
    }

}

