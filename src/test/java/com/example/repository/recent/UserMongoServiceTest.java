package com.example.repository.recent;

import com.example.TestcontainersConfiguration;
import com.example.domain.recent.IdType;
import com.example.domain.recent.entity.UserEntity;
import com.example.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;

import java.time.ZoneId;

/**
 * Created by mskwon on 4/29/24.
 */
@Import(TestcontainersConfiguration.class)
@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class UserMongoServiceTest {

    @Autowired
    private UserMongoService userMongoService;

    @BeforeEach
    void setUp() {
        if (!userMongoService.getUsers().isEmpty()) userMongoService.deleteAll();

        String idKey = "20230822101306774";
        IdType idType = IdType.M;
        userMongoService.save(idKey, idType);
        userMongoService.save("20230822101306775", idType);
        userMongoService.save("20230822101306776", idType);
        userMongoService.save("20230822101306777", idType);
        userMongoService.save("20230822101306778", idType);
        userMongoService.save("20230822101306779", idType);
        userMongoService.save("20230822101306780", idType);
    }

    @Test
    void getUser() {
        UserEntity user = userMongoService.getUser("20230822101306774");
        System.out.println(JsonUtils.toMapperPretty(user));
    }

    @Test
    void findById() {
        UserEntity user = userMongoService.findById("20230822101306774");
        Assertions.assertThat(user.getId()).isEqualTo("20230822101306774");
    }

    @Test
    void getUsers() {
        userMongoService.getUsers()
                .forEach(user -> {
                    System.out.println("%s, expiredAt: %s, updatedAt: %s".formatted(user, user.getExpiredAt().atZone(ZoneId.of("Asia/Seoul")), user.getUpdatedAt().atZone(ZoneId.of("Asia/Seoul")) ));
                });
    }

    @Test
    void deleteById() {
        userMongoService.deleteById("20230822101306774");
    }
}