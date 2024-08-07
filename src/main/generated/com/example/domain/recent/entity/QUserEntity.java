package com.example.domain.recent.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserEntity is a Querydsl query type for UserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEntity extends EntityPathBase<UserEntity> {

    private static final long serialVersionUID = 1527019455L;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> expiredAt = createDateTime("expiredAt", java.time.LocalDateTime.class);

    public final StringPath id = createString("id");

    public final ListPath<RecentSearchEntity, QRecentSearchEntity> recentSearches = this.<RecentSearchEntity, QRecentSearchEntity>createList("recentSearches", RecentSearchEntity.class, QRecentSearchEntity.class, PathInits.DIRECT2);

    public final ListPath<RecentViewEntity, QRecentViewEntity> recentViews = this.<RecentViewEntity, QRecentViewEntity>createList("recentViews", RecentViewEntity.class, QRecentViewEntity.class, PathInits.DIRECT2);

    public final EnumPath<com.example.domain.recent.IdType> type = createEnum("type", com.example.domain.recent.IdType.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QUserEntity(String variable) {
        super(UserEntity.class, forVariable(variable));
    }

    public QUserEntity(Path<? extends UserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserEntity(PathMetadata metadata) {
        super(UserEntity.class, metadata);
    }

}

