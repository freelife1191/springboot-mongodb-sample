package com.example.repository.recent;

import com.example.domain.recent.entity.RecentSearchEntity;
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
public class RecentSearchMongoService {

    private final RecentSearchRepository recentSearchRepository;

    @Transactional
    public RecentSearchEntity save(RecentSearchEntity recentSearchEntity) {
        return recentSearchRepository.save(recentSearchEntity);
    }

    public RecentSearchEntity getRecentSearch(RecentSearchEntity.PrimaryKey primaryKey) {
        return recentSearchRepository.findById(primaryKey).orElse(null);
    }

    public Integer deleteRecentSearchByUserId(String userId) {
        return recentSearchRepository.deleteByUser(UserEntity.builder().id(userId).build());
    }
}
