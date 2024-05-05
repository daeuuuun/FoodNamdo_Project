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

    public final StringPath reactionType = createString("reactionType");

    public final QReviewEntity review;

    public final QUserEntity user;

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
        this.review = inits.isInitialized("review") ? new QReviewEntity(forProperty("review"), inits.get("review")) : null;
        this.user = inits.isInitialized("user") ? new QUserEntity(forProperty("user")) : null;
    }

}

