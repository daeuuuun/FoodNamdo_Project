package org.zerock.foodnamdo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewEntity is a Querydsl query type for ReviewEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewEntity extends EntityPathBase<ReviewEntity> {

    private static final long serialVersionUID = 199551978L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewEntity reviewEntity = new QReviewEntity("reviewEntity");

    public final NumberPath<Double> categoryRatingAmenities = createNumber("categoryRatingAmenities", Double.class);

    public final NumberPath<Double> categoryRatingClean = createNumber("categoryRatingClean", Double.class);

    public final NumberPath<Double> categoryRatingPrice = createNumber("categoryRatingPrice", Double.class);

    public final NumberPath<Double> categoryRatingService = createNumber("categoryRatingService", Double.class);

    public final NumberPath<Double> categoryRatingTaste = createNumber("categoryRatingTaste", Double.class);

    public final NumberPath<Integer> dislike = createNumber("dislike", Integer.class);

    public final NumberPath<Integer> like = createNumber("like", Integer.class);

    public final NumberPath<Double> rating = createNumber("rating", Double.class);

    public final BooleanPath receipt = createBoolean("receipt");

    public final NumberPath<Long> reviewId = createNumber("reviewId", Long.class);

    public final StringPath reviewText = createString("reviewText");

    public final QRstrEntity rstrEntity;

    public final DateTimePath<java.time.LocalDateTime> timeOfCreation = createDateTime("timeOfCreation", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> timeOfRevision = createDateTime("timeOfRevision", java.time.LocalDateTime.class);

    public final QUserEntity userEntity;

    public QReviewEntity(String variable) {
        this(ReviewEntity.class, forVariable(variable), INITS);
    }

    public QReviewEntity(Path<? extends ReviewEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewEntity(PathMetadata metadata, PathInits inits) {
        this(ReviewEntity.class, metadata, inits);
    }

    public QReviewEntity(Class<? extends ReviewEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.rstrEntity = inits.isInitialized("rstrEntity") ? new QRstrEntity(forProperty("rstrEntity")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new QUserEntity(forProperty("userEntity")) : null;
    }

}

