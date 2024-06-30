package com.example.repository.recent;

import com.example.domain.recent.entity.RecentSearchEntity;
import com.example.domain.recent.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

/**
 * Created by mskwon on 4/29/24.
 */
public interface RecentSearchRepository extends MongoRepository<RecentSearchEntity, RecentSearchEntity.PrimaryKey>, QuerydslPredicateExecutor<RecentSearchEntity.PrimaryKey> {

    List<RecentSearchEntity> findByUser(UserEntity user);

    Integer deleteByUser(UserEntity user);
}
