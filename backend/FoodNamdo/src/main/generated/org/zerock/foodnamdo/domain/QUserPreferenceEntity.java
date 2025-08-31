package org.zerock.foodnamdo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserPreferenceEntity is a Querydsl query type for UserPreferenceEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserPreferenceEntity extends EntityPathBase<UserPreferenceEntity> {

    private static final long serialVersionUID = 612859416L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserPreferenceEntity userPreferenceEntity = new QUserPreferenceEntity("userPreferenceEntity");

    public final QCategoryEntity categoryEntity;

    public final NumberPath<Long> preferenceId = createNumber("preferenceId", Long.class);

    public final QUserEntity userEntity;

    public QUserPreferenceEntity(String variable) {
        this(UserPreferenceEntity.class, forVariable(variable), INITS);
    }

    public QUserPreferenceEntity(Path<? extends UserPreferenceEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserPreferenceEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserPreferenceEntity(PathMetadata metadata, PathInits inits) {
        this(UserPreferenceEntity.class, metadata, inits);
    }

    public QUserPreferenceEntity(Class<? extends UserPreferenceEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.categoryEntity = inits.isInitialized("categoryEntity") ? new QCategoryEntity(forProperty("categoryEntity")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new QUserEntity(forProperty("userEntity")) : null;
    }

}

