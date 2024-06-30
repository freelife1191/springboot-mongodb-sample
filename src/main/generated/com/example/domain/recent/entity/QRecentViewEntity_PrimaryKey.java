package com.example.domain.recent.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRecentViewEntity_PrimaryKey is a Querydsl query type for PrimaryKey
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QRecentViewEntity_PrimaryKey extends BeanPath<RecentViewEntity.PrimaryKey> {

    private static final long serialVersionUID = 222922807L;

    public static final QRecentViewEntity_PrimaryKey primaryKey = new QRecentViewEntity_PrimaryKey("primaryKey");

    public final StringPath id = createString("id");

    public final EnumPath<com.example.domain.recent.RecentView.LocaleType> localeType = createEnum("localeType", com.example.domain.recent.RecentView.LocaleType.class);

    public final EnumPath<com.example.domain.recent.RecentView.Location> location = createEnum("location", com.example.domain.recent.RecentView.Location.class);

    public final EnumPath<com.example.domain.recent.Site> site = createEnum("site", com.example.domain.recent.Site.class);

    public final StringPath userId = createString("userId");

    public final EnumPath<com.example.domain.recent.IdType> userType = createEnum("userType", com.example.domain.recent.IdType.class);

    public final EnumPath<com.example.domain.recent.RecentView.Type> viewType = createEnum("viewType", com.example.domain.recent.RecentView.Type.class);

    public QRecentViewEntity_PrimaryKey(String variable) {
        super(RecentViewEntity.PrimaryKey.class, forVariable(variable));
    }

    public QRecentViewEntity_PrimaryKey(Path<? extends RecentViewEntity.PrimaryKey> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRecentViewEntity_PrimaryKey(PathMetadata metadata) {
        super(RecentViewEntity.PrimaryKey.class, metadata);
    }

}

