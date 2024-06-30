package com.example.domain.recent.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecentViewEntity is a Querydsl query type for RecentViewEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecentViewEntity extends EntityPathBase<RecentViewEntity> {

    private static final long serialVersionUID = 712392468L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecentViewEntity recentViewEntity = new QRecentViewEntity("recentViewEntity");

    public final StringPath depDt = createString("depDt");

    public final DateTimePath<java.time.LocalDateTime> expiredAt = createDateTime("expiredAt", java.time.LocalDateTime.class);

    public final DatePath<java.time.LocalDate> groupDate = createDate("groupDate", java.time.LocalDate.class);

    public final StringPath id = createString("id");

    public final EnumPath<com.example.domain.recent.RecentView.LocaleType> localeType = createEnum("localeType", com.example.domain.recent.RecentView.LocaleType.class);

    public final EnumPath<com.example.domain.recent.RecentView.Location> location = createEnum("location", com.example.domain.recent.RecentView.Location.class);

    public final QRecentViewEntity_PrimaryKey primaryKey;

    public final StringPath retDt = createString("retDt");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final QUserEntity user;

    public final EnumPath<com.example.domain.recent.RecentView.Type> viewType = createEnum("viewType", com.example.domain.recent.RecentView.Type.class);

    public QRecentViewEntity(String variable) {
        this(RecentViewEntity.class, forVariable(variable), INITS);
    }

    public QRecentViewEntity(Path<? extends RecentViewEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecentViewEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecentViewEntity(PathMetadata metadata, PathInits inits) {
        this(RecentViewEntity.class, metadata, inits);
    }

    public QRecentViewEntity(Class<? extends RecentViewEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.primaryKey = inits.isInitialized("primaryKey") ? new QRecentViewEntity_PrimaryKey(forProperty("primaryKey")) : null;
        this.user = inits.isInitialized("user") ? new QUserEntity(forProperty("user")) : null;
    }

}

