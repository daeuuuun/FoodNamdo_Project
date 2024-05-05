package org.zerock.foodnamdo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRstrimgEntity is a Querydsl query type for RstrimgEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRstrimgEntity extends EntityPathBase<RstrimgEntity> {

    private static final long serialVersionUID = 1271833400L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRstrimgEntity rstrimgEntity = new QRstrimgEntity("rstrimgEntity");

    public final QRstrEntity restaurant;

    public final NumberPath<Long> rstrImgId = createNumber("rstrImgId", Long.class);

    public final StringPath rstrImgUrl = createString("rstrImgUrl");

    public QRstrimgEntity(String variable) {
        this(RstrimgEntity.class, forVariable(variable), INITS);
    }

    public QRstrimgEntity(Path<? extends RstrimgEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRstrimgEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRstrimgEntity(PathMetadata metadata, PathInits inits) {
        this(RstrimgEntity.class, metadata, inits);
    }

    public QRstrimgEntity(Class<? extends RstrimgEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.restaurant = inits.isInitialized("restaurant") ? new QRstrEntity(forProperty("restaurant")) : null;
    }

}

