package com.example.domain.recent.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRecentSearchEntity_PrimaryKey is a Querydsl query type for PrimaryKey
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QRecentSearchEntity_PrimaryKey extends BeanPath<RecentSearchEntity.PrimaryKey> {

    private static final long serialVersionUID = 698261140L;

    public static final QRecentSearchEntity_PrimaryKey primaryKey = new QRecentSearchEntity_PrimaryKey("primaryKey");

    public final StringPath id = createString("id");

    public final EnumPath<com.example.domain.recent.RecentSearch.Location> location = createEnum("location", com.example.domain.recent.RecentSearch.Location.class);

    public final EnumPath<com.example.domain.recent.RecentSearch.Type> searchType = createEnum("searchType", com.example.domain.recent.RecentSearch.Type.class);

    public final EnumPath<com.example.domain.recent.Site> site = createEnum("site", com.example.domain.recent.Site.class);

    public final StringPath userId = createString("userId");

    public final EnumPath<com.example.domain.recent.IdType> userType = createEnum("userType", com.example.domain.recent.IdType.class);

    public QRecentSearchEntity_PrimaryKey(String variable) {
        super(RecentSearchEntity.PrimaryKey.class, forVariable(variable));
    }

    public QRecentSearchEntity_PrimaryKey(Path<? extends RecentSearchEntity.PrimaryKey> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRecentSearchEntity_PrimaryKey(PathMetadata metadata) {
        super(RecentSearchEntity.PrimaryKey.class, metadata);
    }

}

