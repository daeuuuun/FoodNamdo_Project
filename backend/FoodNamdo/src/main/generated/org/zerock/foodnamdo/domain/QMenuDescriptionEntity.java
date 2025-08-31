package org.zerock.foodnamdo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMenuDescriptionEntity is a Querydsl query type for MenuDescriptionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMenuDescriptionEntity extends EntityPathBase<MenuDescriptionEntity> {

    private static final long serialVersionUID = -1619655247L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMenuDescriptionEntity menuDescriptionEntity = new QMenuDescriptionEntity("menuDescriptionEntity");

    public final StringPath menuCategorySub = createString("menuCategorySub");

    public final NumberPath<Long> menuDescriptionId = createNumber("menuDescriptionId", Long.class);

    public final NumberPath<Integer> menuId = createNumber("menuId", Integer.class);

    public final StringPath menuName = createString("menuName");

    public final NumberPath<Integer> menuPrice = createNumber("menuPrice", Integer.class);

    public final QRstrEntity rstrEntity;

    public QMenuDescriptionEntity(String variable) {
        this(MenuDescriptionEntity.class, forVariable(variable), INITS);
    }

    public QMenuDescriptionEntity(Path<? extends MenuDescriptionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMenuDescriptionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMenuDescriptionEntity(PathMetadata metadata, PathInits inits) {
        this(MenuDescriptionEntity.class, metadata, inits);
    }

    public QMenuDescriptionEntity(Class<? extends MenuDescriptionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.rstrEntity = inits.isInitialized("rstrEntity") ? new QRstrEntity(forProperty("rstrEntity")) : null;
    }

}

