package com.example.repository.recent;

import com.example.domain.recent.IdType;
import com.example.domain.recent.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by mskwon on 4/29/24.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserMongoService {

    private static final Logger log = LoggerFactory.getLogger(UserMongoService.class);
    private final UserRepository userRepository;

    @Transactional
    public UserEntity save(String idKey, IdType idType) {
        UserEntity save = userRepository.save(
                UserEntity.builder()
                        .id(idKey)
                        .type(idType)
                        .expiredAt(LocalDateTime.now().plusYears(1))
                        .build()
        );
        log.debug("save: {}",save);
        return save;
    }

    public UserEntity findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity getUser(String id) {
        return userRepository.findOneById(id);
    }

    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
