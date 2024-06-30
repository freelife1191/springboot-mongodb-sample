package com.example.testcontainers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = DEFINED_PORT)
@DirtiesContext(classMode = AFTER_CLASS)
// 테스트 컨테이너에는 유효한 Docker 설치가 필요합니다.
// 테스트를 실행할 때 유효한 Docker 환경이 있는지 확인하세요.
// 오류 해결 https://velog.io/@ghwns9991/%EC%8A%A4%ED%94%84%EB%A7%81-%EB%B6%80%ED%8A%B8-3.2-%EB%A7%A4%EA%B0%9C%EB%B3%80%EC%88%98-%EC%9D%B4%EB%A6%84-%EC%9D%B8%EC%8B%9D-%EB%AC%B8%EC%A0%9C
// java.lang.IllegalArgumentException: Name for argument of type [java.lang.String] not specified,
// and parameter name information not available via reflection.
// Ensure that the compiler uses the '-parameters' flag.
// 다른 테스트 방법으로 de.flapdoodle.embed.mongo를 사용하는 방법이 있다
// https://www.baeldung.com/spring-boot-mongodb-logging
public class ServiceConnectionLiveTest {

    @Container
    @ServiceConnection
    static MongoDBContainer mongoDbContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"))
                // 이는 컨테이너가 애플리케이션보다 오래 지속될 수 있도록 하는 보다 강력한 대안입니다. 즉, 재사용을 활성화하면 컨테이너를 적극적으로 보존하면서 애플리케이션을 다시 로드하거나 완전히 다시 시작할 수 있습니다
                .withReuse(true);

    @Autowired
    private MiddleEarthCharactersRepository repository;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        if (!repository.findAll().isEmpty()) repository.deleteAll();
    }

    @Test
    void whenRequestingHobbits_thenReturnFrodoAndSam() throws Exception {
        repository.saveAll(List.of(
                new MiddleEarthCharacter("Frodo", "hobbit"),
                new MiddleEarthCharacter("Samwise", "hobbit"),
                new MiddleEarthCharacter("Aragon", "human"),
                new MiddleEarthCharacter("Gandalf", "wizard")
        ));

        when().get("/characters?race=hobbit")
                .then().statusCode(200)
                .and().body("name", hasItems("Frodo", "Samwise"));
        /*
        mockMvc.perform(get("/characters?race=hobbit")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
         */
    }

}
