package org.zerock.foodnamdo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRstrEntity is a Querydsl query type for RstrEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRstrEntity extends EntityPathBase<RstrEntity> {

    private static final long serialVersionUID = 384181137L;

    public static final QRstrEntity rstrEntity = new QRstrEntity("rstrEntity");

    public final SetPath<CategoryEntity, QCategoryEntity> categories = this.<CategoryEntity, QCategoryEntity>createSet("categories", CategoryEntity.class, QCategoryEntity.class, PathInits.DIRECT2);

    public final BooleanPath example = createBoolean("example");

    public final ListPath<MenuDescriptionEntity, QMenuDescriptionEntity> menuDescriptions = this.<MenuDescriptionEntity, QMenuDescriptionEntity>createList("menuDescriptions", MenuDescriptionEntity.class, QMenuDescriptionEntity.class, PathInits.DIRECT2);

    public final BooleanPath relax = createBoolean("relax");

    public final ListPath<RstrCategoryEntity, QRstrCategoryEntity> restaurantCategories = this.<RstrCategoryEntity, QRstrCategoryEntity>createList("restaurantCategories", RstrCategoryEntity.class, QRstrCategoryEntity.class, PathInits.DIRECT2);

    public final ListPath<RstrimgEntity, QRstrimgEntity> restaurantImages = this.<RstrimgEntity, QRstrimgEntity>createList("restaurantImages", RstrimgEntity.class, QRstrimgEntity.class, PathInits.DIRECT2);

    public final ListPath<ReviewEntity, QReviewEntity> reviews = this.<ReviewEntity, QReviewEntity>createList("reviews", ReviewEntity.class, QReviewEntity.class, PathInits.DIRECT2);

    public final StringPath rstrAddress = createString("rstrAddress");

    public final StringPath rstrBusinessHour = createString("rstrBusinessHour");

    public final StringPath rstrClosed = createString("rstrClosed");

    public final BooleanPath rstrDelivery = createBoolean("rstrDelivery");

    public final NumberPath<Integer> rstrFavoriteCount = createNumber("rstrFavoriteCount", Integer.class);

    public final NumberPath<Long> rstrId = createNumber("rstrId", Long.class);

    public final StringPath rstrIntro = createString("rstrIntro");

    public final NumberPath<Double> rstrLa = createNumber("rstrLa", Double.class);

    public final NumberPath<Double> rstrLo = createNumber("rstrLo", Double.class);

    public final StringPath rstrName = createString("rstrName");

    public final NumberPath<Double> rstrNaverRating = createNumber("rstrNaverRating", Double.class);

    public final NumberPath<Integer> rstrNum = createNumber("rstrNum", Integer.class);

    public final BooleanPath rstrParking = createBoolean("rstrParking");

    public final StringPath rstrPermission = createString("rstrPermission");

    public final BooleanPath rstrPet = createBoolean("rstrPet");

    public final BooleanPath rstrPlay = createBoolean("rstrPlay");

    public final StringPath rstrRegion = createString("rstrRegion");

    public final NumberPath<Integer> rstrReviewCount = createNumber("rstrReviewCount", Integer.class);

    public final NumberPath<Double> rstrReviewRating = createNumber("rstrReviewRating", Double.class);

    public final StringPath rstrTel = createString("rstrTel");

    public QRstrEntity(String variable) {
        super(RstrEntity.class, forVariable(variable));
    }

    public QRstrEntity(Path<? extends RstrEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRstrEntity(PathMetadata metadata) {
        super(RstrEntity.class, metadata);
    }

}

