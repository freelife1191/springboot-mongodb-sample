package com.example.repository.recent;

import com.example.domain.recent.entity.RecentViewEntity;
import com.example.domain.recent.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * Created by mskwon on 4/29/24.
 */
public interface RecentViewRepository extends MongoRepository<RecentViewEntity, String>, QuerydslPredicateExecutor<RecentViewEntity> {
    Integer deleteByUser(UserEntity user);
}