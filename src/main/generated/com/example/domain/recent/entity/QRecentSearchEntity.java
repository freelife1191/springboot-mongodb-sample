package com.example.domain.recent.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecentSearchEntity is a Querydsl query type for RecentSearchEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecentSearchEntity extends EntityPathBase<RecentSearchEntity> {

    private static final long serialVersionUID = -472940329L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecentSearchEntity recentSearchEntity = new QRecentSearchEntity("recentSearchEntity");

    public final StringPath code = createString("code");

    public final StringPath data = createString("data");

    public final StringPath division = createString("division");

    public final StringPath etc = createString("etc");

    public final DateTimePath<java.time.LocalDateTime> expiredAt = createDateTime("expiredAt", java.time.LocalDateTime.class);

    public final StringPath from = createString("from");

    public final DatePath<java.time.LocalDate> groupDate = createDate("groupDate", java.time.LocalDate.class);

    public final StringPath id = createString("id");

    public final StringPath keyword = createString("keyword");

    public final EnumPath<com.example.domain.recent.RecentSearch.Location> location = createEnum("location", com.example.domain.recent.RecentSearch.Location.class);

    public final StringPath name = createString("name");

    public final StringPath nationCode = createString("nationCode");

    public final StringPath options = createString("options");

    public final StringPath parent = createString("parent");

    public final QRecentSearchEntity_PrimaryKey primaryKey;

    public final EnumPath<com.example.domain.recent.RecentSearch.Type> searchType = createEnum("searchType", com.example.domain.recent.RecentSearch.Type.class);

    public final StringPath to = createString("to");

    public final StringPath type = createString("type");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final StringPath urId = createString("urId");

    public final QUserEntity user;

    public QRecentSearchEntity(String variable) {
        this(RecentSearchEntity.class, forVariable(variable), INITS);
    }

    public QRecentSearchEntity(Path<? extends RecentSearchEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecentSearchEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecentSearchEntity(PathMetadata metadata, PathInits inits) {
        this(RecentSearchEntity.class, metadata, inits);
    }

    public QRecentSearchEntity(Class<? extends RecentSearchEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.primaryKey = inits.isInitialized("primaryKey") ? new QRecentSearchEntity_PrimaryKey(forProperty("primaryKey")) : null;
        this.user = inits.isInitialized("user") ? new QUserEntity(forProperty("user")) : null;
    }

}

