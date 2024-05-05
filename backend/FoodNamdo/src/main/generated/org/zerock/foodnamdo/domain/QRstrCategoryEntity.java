package org.zerock.foodnamdo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRstrCategoryEntity is a Querydsl query type for RstrCategoryEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRstrCategoryEntity extends EntityPathBase<RstrCategoryEntity> {

    private static final long serialVersionUID = 2140407855L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRstrCategoryEntity rstrCategoryEntity = new QRstrCategoryEntity("rstrCategoryEntity");

    public final QCategoryEntity categoryId;

    public final QRstrEntity restaurant;

    public final NumberPath<Long> rstr_category_id = createNumber("rstr_category_id", Long.class);

    public QRstrCategoryEntity(String variable) {
        this(RstrCategoryEntity.class, forVariable(variable), INITS);
    }

    public QRstrCategoryEntity(Path<? extends RstrCategoryEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRstrCategoryEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRstrCategoryEntity(PathMetadata metadata, PathInits inits) {
        this(RstrCategoryEntity.class, metadata, inits);
    }

    public QRstrCategoryEntity(Class<? extends RstrCategoryEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.categoryId = inits.isInitialized("categoryId") ? new QCategoryEntity(forProperty("categoryId")) : null;
        this.restaurant = inits.isInitialized("restaurant") ? new QRstrEntity(forProperty("restaurant")) : null;
    }

}

