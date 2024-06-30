package com.example.config.mongo;

import com.example.domain.recent.entity.UserEntity;
import com.example.repository.recent.RecentSearchMongoService;
import com.example.repository.recent.RecentViewMongoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.event.*;
import org.springframework.stereotype.Component;

/**
 * Created by mskwon on 2024. 5. 14..
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserCascadeDeleteMongoEventListener extends AbstractMongoEventListener<UserEntity> {

    private final RecentSearchMongoService recentSearchMongoService;
    private final RecentViewMongoService recentViewMongoService;

    /**
     * Evnet가 호출될때마다 항상 먼저 실행됨
     * @param event
     */
    @Override
    public void onApplicationEvent(MongoMappingEvent<?> event) {
        // log.info("## onApplicationEvent");
        super.onApplicationEvent(event);
    }

    /**
     * 수정전 호출
     * @param event never {@literal null}.
     */
    @Override
    public void onBeforeConvert(BeforeConvertEvent<UserEntity> event) {
        // log.info("## onBeforeConvert");
        super.onBeforeConvert(event);
    }

    /**
     * 수정후 호출
     * @param event will never be {@literal null}.
     */
    @Override
    public void onAfterConvert(AfterConvertEvent<UserEntity> event) {
        // log.info("## onAfterConvert");
        super.onAfterConvert(event);
    }

    /**
     * 조회시 호출
     * @param event will never be {@literal null}.
     */
    @Override
    public void onAfterLoad(AfterLoadEvent<UserEntity> event) {
        String userId = event.getSource().get("_id").toString();
        // log.info("## onAfterLoad: {}",userId);
        super.onAfterLoad(event);
    }

    /**
     * 삭제전 호출
     * @param event will never be {@literal null}.
     */
    @Override
    public void onBeforeDelete(BeforeDeleteEvent<UserEntity> event) {
        String userId = event.getSource().get("_id").toString();
        // log.info("## onBeforeDelete: {}",userId);
        super.onBeforeDelete(event);
    }

    /**
     * 삭제후 호출
     * @param userEvent will never be {@literal null}.
     */
    @Override
    public void onAfterDelete(AfterDeleteEvent<UserEntity> userEvent) {
        String userId = userEvent.getSource().get("_id").toString();
        Integer deleteCountRecentSearch = recentSearchMongoService.deleteRecentSearchByUserId(userId);
        Integer deleteCountRecentVeiw = recentViewMongoService.deleteRecentViewByUserId(userId);
        log.info("## onAfterDelete: {}, deleteCountRecentSearch: {}, deleteCountRecentView: {}",userId, deleteCountRecentSearch, deleteCountRecentVeiw);
        super.onAfterDelete(userEvent);
    }
}
