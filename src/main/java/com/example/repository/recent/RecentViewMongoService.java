package com.example.repository.recent;

import com.example.domain.recent.entity.RecentViewEntity;
import com.example.domain.recent.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mskwon on 4/30/24.
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecentViewMongoService {

    private final RecentViewRepository recentViewRepository;

    @Transactional
    public RecentViewEntity save(RecentViewEntity recentViewEntity) {
        return recentViewRepository.save(recentViewEntity);
    }

    @Transactional
    public Integer deleteRecentViewByUserId(String userId) {
        return recentViewRepository.deleteByUser(UserEntity.builder().id(userId).build());
    }
}