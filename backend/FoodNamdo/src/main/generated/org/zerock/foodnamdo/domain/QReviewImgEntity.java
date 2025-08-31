package org.zerock.foodnamdo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewImgEntity is a Querydsl query type for ReviewImgEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewImgEntity extends EntityPathBase<ReviewImgEntity> {

    private static final long serialVersionUID = 546664479L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewImgEntity reviewImgEntity = new QReviewImgEntity("reviewImgEntity");

    public final QReviewEntity reviewEntity;

    public final NumberPath<Long> reviewImgId = createNumber("reviewImgId", Long.class);

    public final StringPath reviewImgUrl = createString("reviewImgUrl");

    public QReviewImgEntity(String variable) {
        this(ReviewImgEntity.class, forVariable(variable), INITS);
    }

    public QReviewImgEntity(Path<? extends ReviewImgEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewImgEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewImgEntity(PathMetadata metadata, PathInits inits) {
        this(ReviewImgEntity.class, metadata, inits);
    }

    public QReviewImgEntity(Class<? extends ReviewImgEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.reviewEntity = inits.isInitialized("reviewEntity") ? new QReviewEntity(forProperty("reviewEntity"), inits.get("reviewEntity")) : null;
    }

}

