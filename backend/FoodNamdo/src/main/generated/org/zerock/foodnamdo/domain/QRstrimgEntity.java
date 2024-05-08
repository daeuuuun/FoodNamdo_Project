package org.zerock.foodnamdo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRstrImgEntity is a Querydsl query type for RstrImgEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRstrImgEntity extends EntityPathBase<RstrImgEntity> {

    private static final long serialVersionUID = -1019165928L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRstrImgEntity rstrImgEntity = new QRstrImgEntity("rstrImgEntity");

    public final QRstrEntity rstrEntity;

    public final NumberPath<Long> rstrImgId = createNumber("rstrImgId", Long.class);

    public final StringPath rstrImgUrl = createString("rstrImgUrl");

    public QRstrImgEntity(String variable) {
        this(RstrImgEntity.class, forVariable(variable), INITS);
    }

    public QRstrImgEntity(Path<? extends RstrImgEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRstrImgEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRstrImgEntity(PathMetadata metadata, PathInits inits) {
        this(RstrImgEntity.class, metadata, inits);
    }

    public QRstrImgEntity(Class<? extends RstrImgEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.rstrEntity = inits.isInitialized("rstrEntity") ? new QRstrEntity(forProperty("rstrEntity")) : null;
    }

}

