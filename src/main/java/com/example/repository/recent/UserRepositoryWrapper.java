package com.example.repository.recent;

import com.example.domain.recent.entity.UserEntity;

/**
 * Created by mskwon on 5/3/24.
 */
public interface UserRepositoryWrapper {

    UserEntity findByUserId(String id);
}
