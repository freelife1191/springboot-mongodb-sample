package com.example.repository.recent;

import com.example.domain.recent.entity.UserEntity;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * Aggregation 참고: https://velog.io/@ddclub12/Webflux%EC%97%90%EC%84%9C-Mongo-Aggregation-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0
 * Created by mskwon on 4/29/24.
 */
public interface UserRepository extends MongoRepository<UserEntity, String>, QuerydslPredicateExecutor<UserEntity>, UserRepositoryWrapper {

    @Aggregation({"{$match: {_id: ?0}}"
            ,"{$lookup:  {from:  \"recent_search\", localField:  \"_id\", foreignField: \"_id.userId\",as: \"recentSearches\"}}"
            ,"{$lookup:  {from:  \"recent_view\", localField:  \"_id\", foreignField: \"_id.userId\",as: \"recentViews\"}}"})
    UserEntity findOneById(String id);
}
