package com.example.repository.recent;

import com.example.TestcontainersConfiguration;
import com.example.domain.recent.IdType;
import com.example.domain.recent.RecentSearch;
import com.example.domain.recent.Site;
import com.example.domain.recent.entity.RecentSearchEntity;
import com.example.domain.recent.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by mskwon on 5/2/24.
 */
@Import(TestcontainersConfiguration.class)
@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class RecentSearchMongoServiceTest {

    @Autowired
    private UserMongoService userMongoService;
    @Autowired
    private RecentSearchMongoService recentSearchMongoService;
    @Autowired
    private RecentSearchRepository recentSearchRepository;

    @BeforeEach
    void setUp() {
        if (!userMongoService.getUsers().isEmpty()) userMongoService.deleteAll();

        String idKey = "20230822101306774";
        IdType idType = IdType.M;
        UserEntity user = userMongoService.save(idKey, idType);
        RecentSearchEntity.PrimaryKey primaryKey = RecentSearchEntity.PrimaryKey.builder()
                .site(Site.TRAVEL)
                .userType(IdType.M)
                .userId(user.getId())
                .build();
        recentSearchMongoService.save(
                RecentSearchEntity.builder()
                        .groupDate(LocalDate.now())
                        .location(RecentSearch.Location.PACKAGE)
                        .searchType(RecentSearch.Type.PRODUCT)
                        .keyword("[코타키나발루] 수트라하버 마젤란 3박5일+자유일정")
                        .id("3417666")
                        .parent("4878-74071")
                        .from("2024-03-31")
                        .to("2024-04-04")
                        .user(user)
                        .expiredAt(LocalDateTime.now().plusYears(1))
                        .build().setPrimaryKey(primaryKey.setId("3417666")));
        recentSearchMongoService.save(
                RecentSearchEntity.builder()
                        .groupDate(LocalDate.now())
                        .location(RecentSearch.Location.PACKAGE)
                        .searchType(RecentSearch.Type.PRODUCT)
                        .keyword("[코타키나발루2] 수트라하버 마젤란 3박5일+자유일정")
                        .id("3417665")
                        .parent("4878-74072")
                        .from("2024-03-31")
                        .to("2024-04-04")
                        .user(user)
                        .expiredAt(LocalDateTime.now().plusYears(1))
                        .build().setPrimaryKey(primaryKey.setId("3417665")));
        recentSearchMongoService.save(
                RecentSearchEntity.builder()
                        .groupDate(LocalDate.now())
                        .location(RecentSearch.Location.PACKAGE)
                        .searchType(RecentSearch.Type.PRODUCT)
                        .keyword("[코타키나발루3] 수트라하버 마젤란 3박5일+자유일정")
                        .id("3417664")
                        .parent("4878-74073")
                        .from("2024-03-31")
                        .to("2024-04-04")
                        .user(user)
                        .expiredAt(LocalDateTime.now().plusYears(1))
                        .build().setPrimaryKey(primaryKey.setId("3417664")));
    }

    @Test
    void getRecentSearch() {
        RecentSearchEntity recentSearch = recentSearchMongoService.getRecentSearch(RecentSearchEntity.PrimaryKey.builder()
                .site(Site.TRAVEL)
                .location(RecentSearch.Location.PACKAGE)
                .userType(IdType.M)
                .userId("20230822101306774")
                .id("3417666")
                .build());
        System.out.println(recentSearch);
    }

    @Test
    void getRecentSearches() {
        List<RecentSearchEntity> byUser = recentSearchRepository.findByUser(UserEntity.builder()
                .id("20230822101306774")
                .build());
        byUser.forEach(System.out::println);
    }

}