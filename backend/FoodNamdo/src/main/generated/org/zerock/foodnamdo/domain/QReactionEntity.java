package org.zerock.foodnamdo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReactionEntity is a Querydsl query type for ReactionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReactionEntity extends EntityPathBase<ReactionEntity> {

    private static final long serialVersionUID = 1996375611L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReactionEntity reactionEntity = new QReactionEntity("reactionEntity");

    public final NumberPath<Long> reactionId = createNumber("reactionId", Long.class);

    public final EnumPath<ReactionType> reactionType = createEnum("reactionType", ReactionType.class);

    public final QReviewEntity reviewEntity;

    public final QUserEntity userEntity;

    public QReactionEntity(String variable) {
        this(ReactionEntity.class, forVariable(variable), INITS);
    }

    public QReactionEntity(Path<? extends ReactionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReactionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReactionEntity(PathMetadata metadata, PathInits inits) {
        this(ReactionEntity.class, metadata, inits);
    }

    public QReactionEntity(Class<? extends ReactionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.reviewEntity = inits.isInitialized("reviewEntity") ? new QReviewEntity(forProperty("reviewEntity"), inits.get("reviewEntity")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new QUserEntity(forProperty("userEntity")) : null;
    }

}

