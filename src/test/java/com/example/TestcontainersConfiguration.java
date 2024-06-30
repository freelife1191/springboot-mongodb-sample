package com.example;

import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

// https://www.baeldung.com/spring-boot-built-in-testcontainers
// https://github.com/testcontainers/testcontainers-java-spring-boot-quickstart
@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

    @Bean
    @RestartScope
    @ServiceConnection
    MongoDBContainer mongoDbContainer() {
        return new MongoDBContainer(DockerImageName.parse("mongo:latest"))
                // 이는 컨테이너가 애플리케이션보다 오래 지속될 수 있도록 하는 보다 강력한 대안입니다. 즉, 재사용을 활성화하면 컨테이너를 적극적으로 보존하면서 애플리케이션을 다시 로드하거나 완전히 다시 시작할 수 있습니다
                .withReuse(true);
    }

}
