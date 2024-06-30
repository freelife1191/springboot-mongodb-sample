package com.example.repository.recent;

import com.example.TestcontainersConfiguration;
import com.example.domain.recent.IdType;
import com.example.domain.recent.RecentView;
import com.example.domain.recent.Site;
import com.example.domain.recent.entity.RecentViewEntity;
import com.example.domain.recent.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by mskwon on 5/5/24.
 */
@Import(TestcontainersConfiguration.class)
@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class RecentViewMongoServiceTest {

    @Autowired
    private UserMongoService userMongoService;
    @Autowired
    private RecentViewMongoService recentViewMongoService;

    @Test
    void save() {
        String idKey = "20230822101306774";
        IdType idType = IdType.M;
        UserEntity user = userMongoService.save(idKey, idType);
        RecentViewEntity.PrimaryKey primaryKey = RecentViewEntity.PrimaryKey.builder()
                .site(Site.TRAVEL)
                .userType(IdType.M)
                .userId(user.getId())
                .build();
        recentViewMongoService.save(RecentViewEntity.builder()
                        .groupDate(LocalDate.now())
                        .viewType(RecentView.Type.HOTEL)
                        .localeType(RecentView.LocaleType.DOM)
                        .location(RecentView.Location.HOTEL)
                        .id("100249")
                        .user(user)
                        .expiredAt(LocalDateTime.now().plusYears(1))
                        .build().setPrimaryKey(primaryKey));
        // 제주 아쿠아플라넷 종합권+선녀와나무꾼 입장권
        recentViewMongoService.save(RecentViewEntity.builder()
                        .groupDate(LocalDate.now())
                        .viewType(RecentView.Type.TOACT)
                        .localeType(RecentView.LocaleType.DOM)
                        .location(RecentView.Location.TOACT)
                        .id("PRD3000057621")
                        .user(user)
                        .expiredAt(LocalDateTime.now().plusYears(1))
                        .build().setPrimaryKey(primaryKey));
        recentViewMongoService.save(RecentViewEntity.builder()
                        .groupDate(LocalDate.now())
                        .viewType(RecentView.Type.AIRPORT)
                        .localeType(RecentView.LocaleType.INT)
                        .location(RecentView.Location.AIR)
                        .id("NRT")
                        .depDt("2024-07-07")
                        .retDt("2024-07-09")
                        .user(user)
                        .expiredAt(LocalDateTime.now().plusYears(1))
                        .build().setPrimaryKey(primaryKey));
        recentViewMongoService.save(RecentViewEntity.builder()
                        .groupDate(LocalDate.now())
                        .viewType(RecentView.Type.PACKAGE)
                        .localeType(RecentView.LocaleType.INT)
                        .location(RecentView.Location.PACKAGE)
                        .id("21560-106783_4796701")
                        .user(user)
                        .expiredAt(LocalDateTime.now().plusYears(1))
                        .build().setPrimaryKey(primaryKey));
    }
}