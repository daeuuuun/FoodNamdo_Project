package org.zerock.foodnamdo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPreferencesEntity is a Querydsl query type for PreferencesEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPreferencesEntity extends EntityPathBase<PreferencesEntity> {

    private static final long serialVersionUID = 22892492L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPreferencesEntity preferencesEntity = new QPreferencesEntity("preferencesEntity");

    public final StringPath preference = createString("preference");

    public final NumberPath<Long> preferenceId = createNumber("preferenceId", Long.class);

    public final QUserEntity user;

    public QPreferencesEntity(String variable) {
        this(PreferencesEntity.class, forVariable(variable), INITS);
    }

    public QPreferencesEntity(Path<? extends PreferencesEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPreferencesEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPreferencesEntity(PathMetadata metadata, PathInits inits) {
        this(PreferencesEntity.class, metadata, inits);
    }

    public QPreferencesEntity(Class<? extends PreferencesEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUserEntity(forProperty("user")) : null;
    }

}

