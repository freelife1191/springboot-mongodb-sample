package com.example.repository.recent.impl;

import com.example.domain.recent.entity.UserEntity;
import com.example.repository.recent.UserRepositoryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.example.domain.recent.entity.QUserEntity.userEntity;

/**
 * Created by mskwon on 5/3/24.
 */
@Slf4j
@Repository
public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryWrapper {

    public UserRepositoryImpl(MongoOperations operations) {
        super(operations);
    }

    @Override
    public UserEntity findByUserId(String id) {
        return from(userEntity).where(userEntity.id.eq(id)).fetchOne();
    }
}
