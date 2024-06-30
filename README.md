# SpringBoot MongoDB Sample

<!-- TOC -->
* [SpringBoot MongoDB Sample](#springboot-mongodb-sample)
  * [🚦 Overview](#-overview)
  * [🚦 Spring Boot Data MongoDB 개발환경 셋팅](#-spring-boot-data-mongodb-개발환경-셋팅)
    * [📌 build 환경 구성](#-build-환경-구성)
      * [🛠️ Gradle](#-gradle)
      * [🛠️ Maven](#-maven)
    * [📌 `application.yml` 설정](#-applicationyml-설정)
    * [📌 Auditing 설정](#-auditing-설정)
    * [📌 Repository 설정](#-repository-설정)
  * [🚦 Spring Boot Data MongoDB Entity 구성](#-spring-boot-data-mongodb-entity-구성)
    * [📌 복합키와 인덱스 구성](#-복합키와-인덱스-구성)
      * [▶︎ MongoDB 복합키(`@Embeddable`, `@EmbeddedId`) 구성](#-mongodb-복합키embeddable-embeddedid-구성)
      * [▶︎ MongoDB Expired 인덱스(`@Indexed`) 구성](#-mongodb-expired-인덱스indexed-구성)
    * [📌 Entity(`@Entity`, `@Document`) 구성 및 연관관계(`@DBRef`) 설정](#-entityentity-document-구성-및-연관관계dbref-설정)
      * [▶︎ UserEntity(유저)](#-userentity유저)
      * [▶︎ RecentSearchEntity(최근 검색)](#-recentsearchentity최근-검색)
      * [▶︎ RecentViewEntity(최근 조회)](#-recentviewentity최근-조회)
  * [🚦Spring Boot Data MongoDB Aggregation(`$lookup`) 예제](#spring-boot-data-mongodb-aggregationlookup-예제)
    * [📌 MongoDB Aggregation `$match`와 `$lookup`을 사용한 Join Query 예제](#-mongodb-aggregation-match와-lookup을-사용한-join-query-예제)
  * [🚦Spring Boot DataMongoDB QueryDSL 및 Wrapper를 사용한 구성 예제](#spring-boot-datamongodb-querydsl-및-wrapper를-사용한-구성-예제)
    * [📌 MongoDB QueryDSL 및 Wrapper 구성](#-mongodb-querydsl-및-wrapper-구성)
  * [🚦Spring Boot Data MongoDB Service 구현 예제](#spring-boot-data-mongodb-service-구현-예제)
    * [📌 Service 구현](#-service-구현)
      * [▶︎ UserService(유저 서비스) 구현](#-userservice유저-서비스-구현)
      * [▶︎ RecentSearchMongoService(최근 검색 서비스) 구현](#-recentsearchmongoservice최근-검색-서비스-구현)
      * [▶︎ RecentViewMongoService(최근 조회 서비스) 구현](#-recentviewmongoservice최근-조회-서비스-구현)
    * [📌 `AbstractMongoEventListener` Lifecycle Event를 활용한 Cascade 삭제](#-abstractmongoeventlistener-lifecycle-event를-활용한-cascade-삭제)
      * [▶︎ Cascade 삭제 구현](#-cascade-삭제-구현)
  * [🚦Spring Boot Data MongoDB Test 코드 작성](#spring-boot-data-mongodb-test-코드-작성)
    * [📌 Service 테스트 코드 작성](#-service-테스트-코드-작성)
      * [▶︎ UserMongoServiceTest(유저 서비스 테스트)](#-usermongoservicetest유저-서비스-테스트)
      * [▶︎ RecentSearchMongoServiceTest(최근 검색 서비스 테스트)](#-recentsearchmongoservicetest최근-검색-서비스-테스트)
      * [▶︎ RecentViewMongoServiceTest(최근 조회 서비스 테스트)](#-recentviewmongoservicetest최근-조회-서비스-테스트)
<!-- TOC -->

## 🚦 Overview

검색 기능 개발을 위해 데이터 관리의 유연함과 Expired 처리 그리고 조회 성능 개선을 위해 MongoDB를 도입하기 위한 Sample 구성

Sample 구성은 [Testcontainers](https://testcontainers.com/)를 통해 구성 및 테스트를 완료 하였으며 [Built-in Testcontainers Support in Spring Boot](https://www.baeldung.com/spring-boot-built-in-testcontainers) 내용을 참고함

AWS DocumentDB의 Current 버전은 MongoDB 5.0 이고 MongoDB Release 버전은 7.0 이지만 5.0 이상의 버전에서 많은 문제점들이 개선되고 성능이 대폭 향상되어 AWS 사용자라면 AWS DocumentDB를 도입하여 사용하는 것이 연동성이 가장 좋음

SpringBoot에서 MongoDB를 활용하는 방법이 매우 다양하지만 MongoDB 문법에 익숙하지 않으면 가장 Simple 한 방법으로 JPA, QueryDSL 조합과 비슷한  [Annotation](https://yang1s.tistory.com/30)과 [QueryDSL](https://beanbroker.github.io/2020/08/16/Spring/mongodb_srping3/)을 활용하거나 MongoTemplate MongoDB를 사용하는 것을 추천

MongoDB 문법에 매우 익숙하다면 [@Query](https://wonyong-jang.github.io/database/2021/08/17/DB-MongoDB-between-query.html) 어노테이션과 [Operation](https://velog.io/@jmh6672/Spring%EC%97%90%EC%84%9C-MongoDB-Aggregation) 들을 활용해서 MongoDB Query와 유사하게 사용할 수 있는 방법도 있음


- DocumentDB 검토 참고
  - https://www.mongodb.com/docs/manual/release-notes/5.0/
  - [AWS DocumentDB(MongoDB) 데이터 구조 설계](docs/DocumentDB_Data_Structure_Design.md)

- Spring Data MongoDB 참고
  - https://yang1s.tistory.com/30
  - https://www.baeldung.com/spring-data-mongodb-tutorial
  - https://www.baeldung.com/queries-in-spring-data-mongodb
  - https://www.baeldung.com/tag/spring-data-mongodb
  - https://beanbroker.github.io/2020/08/16/Spring/mongodb_srping3/
  - https://velog.io/@jmh6672/Spring%EC%97%90%EC%84%9C-MongoDB-Aggregation

---

## 🚦 Spring Boot Data MongoDB 개발환경 셋팅


- [AWS DocumentDB 개발환경 셋팅](docs/DocumentDB_Environment.MD)
- Spring Data MongoDB 관련 내용 참고
  - https://devel-repository.tistory.com/tag/Spring%20Data%20MongoDB

JDK 21 환경에서 진행

### 📌 build 환경 구성

- build 설정 참고
  - https://samori.tistory.com/60
  - https://www.inflearn.com/chats/872346/spring-boot-3-querydsl-%EC%84%A4%EC%A0%95-%EA%B3%B5%EC%9C%A0-%EB%93%9C%EB%A6%BD%EB%8B%88%EB%8B%A4-mongodb
  - https://velog.io/@404-nut-pound/Spring-Boot-3-JPAMongoDB-Querydsl-%EC%84%A4%EC%A0%95
  - https://github.com/zeno1030/Backend-wildCoding/blob/main/build.gradle 

#### 🛠️ Gradle

```groovy
configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

dependencies {
    
    ...

    // MongoDB
    implementation 'org.springframework.boot:spring-boot-starter-data-mongodb'
    implementation 'com.querydsl:querydsl-core:5.1.0'
    implementation('com.querydsl:querydsl-mongodb:5.1.0') {
        exclude group: "org.mongodb", module: "mongo-java-driver"
    }
    annotationProcessor 'com.querydsl:querydsl-apt:5.1.0:jakarta'
    annotationProcessor 'jakarta.persistence:jakarta.persistence-api'
    annotationProcessor 'jakarta.annotation:jakarta.annotation-api'
}
```

#### 🛠️ Maven

```xml
<dependencies>
    <!-- MongoDB -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-mongodb</artifactId>
    </dependency>
    <dependency>
        <groupId>com.querydsl</groupId>
        <artifactId>querydsl-core</artifactId>
        <version>5.1.0</version>
    </dependency>
    <dependency>
        <groupId>com.querydsl</groupId>
        <artifactId>querydsl-mongodb</artifactId>
        <version>5.1.0</version>
        <exclusions>
            <exclusion>
              <groupId>org.mongodb</groupId>
              <artifactId>mongo-java-driver</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    <dependency>
        <groupId>com.querydsl</groupId>
        <artifactId>querydsl-apt</artifactId>
        <version>5.1.0</version>
        <classifier>jakarta</classifier>
    </dependency>
    <dependency>
        <groupId>jakarta.persistence</groupId>
        <artifactId>jakarta.persistence-api</artifactId>
    </dependency>
    <dependency>
        <groupId>jakarta.annotation</groupId>
        <artifactId>jakarta.annotation-api</artifactId>
    </dependency>
</dependencies>
```

### 📌 `application.yml` 설정

`application.yml` 에 MongoDB 접속 및 기타 설정을 추가

- 참고
  - https://docs.spring.io/spring-data/mongodb/reference/mongodb/mapping/mapping-index-management.html
  - https://docs.spring.io/spring-data/mongodb/docs/current-SNAPSHOT/reference/html/#reference
  - https://www.baeldung.com/spring-data-mongodb-index-annotations-converter
- 트러블슈팅: https://velog.io/@hadmarine/problems-while-using-aws-documentdb-for-the-first-time
  - 접속시 `directConnection=true` 옵션만으로 접속을 시도하니 오류가 발생하여
    `directConnection=true&replicaSet=rs0&readPreference=secondaryPreferred&retryWrites=false&w=majority` 옵션으로 변경하여 접속 성공

```yaml
spring:
  profiles:
    active: dev
  data:
    mongodb:
      uri: mongodb://username:password@localhost:27017/database?directConnection=true&replicaSet=rs0&readPreference=secondaryPreferred&retryWrites=false&w=majority
      database: database
      # 이 설정을 해야지 Index를 자동으로 생성할 수 있음
      auto-index-creation: true
```

### 📌 Auditing 설정

MongoDB에 데이터를 insert, update 할때 자동으로 수정 시간 업데이트가 적용되도록 하기 위해서 JPA와 마찬가지로 Auditing 설정이 필요함

설정방식은 JPA와 거의 동일함

- 참고
  - https://beanbroker.github.io/2020/08/16/Spring/mongodb_srping4/

Entity에서 JPA와 같이 자동으로 `@LastModifiedDate` 로 **updatedAt**을 남기거나 `@CreatedDate` 로 **createdAt**을 남기기 위해서는 Auditing 설정을 추가 해주어야 한다

```java
@EnableMongoAuditing
...
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

Entity 적용 예시

```java
@Entity
@Document(collection = "user")
public class UserEntity implements Serializable {

    ...
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;

    ...
    
}
```

### 📌 Repository 설정

`spring-boot-starter-data-mongodb`는 기본적으로 `MongoRepository`를 상속받는 것 만으로 추상화된 MongoDB 인터페이스를 사용하여 JPA와 비슷하게 Query를 사용할 수 있다

- 참고
  - https://devel-repository.tistory.com/77
  - https://beanbroker.github.io/2020/08/16/Spring/mongodb_srping3/

`MongoRepository`를 사용하기 위해서는 `@EnableMongoRepositories`를 추가 해주어야 한다

```java
@EnableMongoRepositories
...
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```


`UserRepository`: `MongoRepsitory`, `QuerydslPredicateExecutor`, `UserRepositoryWrapper`를 상속

```java
public interface UserRepository extends MongoRepository<UserEntity, String>, QuerydslPredicateExecutor<UserEntity>, UserRepositoryWrapper {
}
```


`UserRepositoryWrapper`: MongoDB QueryDSL을 사용하기 위한 인터페이스
```java
public interface UserRepositoryWrapper {
}
```


`UserRepositoryImpl`: UserRepositoryWrapper 구현체

```java
@Repository
public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryWrapper {

    public UserRepositoryImpl(MongoOperations operations) {
        super(operations);
    }
}
```


사용 예시

```java
@Test
void findById() {
    UserEntity user = userMongoService.findById("20230822101306774");
    Assertions.assertThat(user.getId()).isEqualTo("20230822101306774");
}
```

---

## 🚦 Spring Boot Data MongoDB Entity 구성

Spring Boot Data MongoDB Entity 구성 예제는 최근 본 여행 Schema 설계를 Entity로 구성한 예제이다

![img.png](attachments/img1.png)

- 예제 스키마 구조 참고
  - [AWS DocumentDB(MongoDB) 데이터 구조 설계](docs/DocumentDB_Data_Structure_Design.md)


### 📌 복합키와 인덱스 구성

복합키를 설정하지 않으면 MongoDB는 기본적으로 MongoDB 자체적으로 유니크한 ObjectId로 ID를 생성한다

아래의 예시는 복합키를 구성할 필요가 있거나 Expired 설정을 하기위한 내용이다

#### ▶︎ MongoDB 복합키(`@Embeddable`, `@EmbeddedId`) 구성

- 참고
  - [복합키와 인덱스키 (feat. 스프링부트)](https://velog.io/@sh93/%EB%B3%B5%ED%95%A9%ED%82%A4%EC%99%80-%EC%9D%B8%EB%8D%B1%EC%8A%A4%ED%82%A4-feat.-%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8)

PrimaryKey innerClass 객체로 복합키를 구성한 예시

```java
@Entity
@Document(collection = "recent_view")
public class RecentViewEntity implements Serializable {

    ...

    @EmbeddedId
    private PrimaryKey primaryKey;

    public RecentViewEntity setPrimaryKey(PrimaryKey primaryKey) {
        this.primaryKey = primaryKey.setRecentView(this);
        return this;
    }

    @Data
    @Embeddable
    public static class PrimaryKey {
        private Site site;
        private IdType userType;
        private String userId;
        private RecentView.Type viewType;
        private RecentView.LocaleType localeType;
        private RecentView.Location location;
        private String id;

        public PrimaryKey setRecentView(RecentViewEntity recentView) {
            this.viewType = recentView.getViewType();
            this.localeType = recentView.getLocaleType();
            this.location = recentView.getLocation();
            this.id = recentView.getId();
            return this;
        }
    }
}
```

아래와 같이 _id 인덱스 가 자동 생성됨 (MongoDB Compass 에서 확인)

![img.png](attachments/img2.png)


#### ▶︎ MongoDB Expired 인덱스(`@Indexed`) 구성

- 참고
  - https://www.mongodb.com/docs/v5.0/tutorial/expire-data/
  - https://medium.com/@isurumaldeniya_15827/creating-ttl-value-for-single-creating-ttl-value-for-single-mongo-db-collection-using-spring-boot-db87dcabe56c
  - https://wiki.yowu.dev/ko/Knowledge-base/NoSQL/mongodb-time-to-live-ttl-collections-expiring-data-automatically


- `@Indexed`로 Expired Index 설정 Entity 설정으로 최초에 설정시 재설정을 위해선 Index를 삭제하고 다시설정해야 됨
  - `name`: Expired 처리를 위해 참고할 속성명(`expiredAt`)을 적는다
  - `expireAfterSeconds`: 0은 설정하지 않음, 0이상은 Expired가 설정되어 해당 초 후에 Expired 처리됨

MongoDB Index로 `expiredAt`을 `expiredAfterSeconds` = 0으로 설정하면 `expiredAfterSeconds` 설정은 사용하지 않고 `expiredAt` 데이터를 참고하여 Expired 처리가 되도록 설정된다

```java
@Entity
@Document(collection = "user")
public class UserEntity implements Serializable {
    ...
    @Indexed(name = "expiredAt", expireAfterSeconds = 0)
    private LocalDateTime expiredAt;
    ...
}
```

Service에서 데이터 저장시 `expiredAt` 시간을 설정

```java
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserMongoService {
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
        return save;
    }
    ...
}
```

아래와 같이 expiredAt 인덱스가 자동 생성됨 (MongoDB Compass 에서 확인)

![img.png](attachments/img3.png)

---

### 📌 Entity(`@Entity`, `@Document`) 구성 및 연관관계(`@DBRef`) 설정

예제는 부모 참조 형식으로 Entity를 설계 했으며 `UserEntity`는 `RecentSearchEntity`와 `RecentViewEntity`의 부모이고 `RecentSearchEntity`와 `RecentViewEntity`는 각각 `UserEntity`를 레퍼런스 키로 가지고 있어 `UserEntity`에서 참조된 데이터를 처리할 수 있다

- 부모 참조 참고: https://meetup.nhncloud.com/posts/276


#### ▶︎ UserEntity(유저)

- 트러블 슈팅
  - https://33233.tistory.com/1
  - https://velog.io/@thsruddl77/Basic-attribute-type-should-not-be-a-container
  - `@DBRef` 설정시 `@OneToMany`, `@ManyToOne`, `@ManyToMany`, `@OneToOne`또는 `@ElementCollections`를 설정하지 않으면 오류가 발생

부모인 `UserEntity`만 조회하면 참조된 `RecentSearchEntity`와 `RecentViewEntity`를 `Aggregation` 조회하여 각각 `recentSearches`, `recentViews` 객체에 담는다

```java
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Document(collection = "user")
public class UserEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -469161004196114143L;

    @Id
    private String id;

    private IdType type;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Indexed(name = "expiredAt", expireAfterSeconds = 0)
    private LocalDateTime expiredAt;

    // @ElementCollection // 1:N 매핑으로 테이블에 데이터가 저장
    // private List<String> lastViews; // LastView에 사용될 최근 20개 목록을 유지

    @DBRef
    @OneToMany
    private List<RecentSearchEntity> recentSearches;

    @DBRef
    @OneToMany
    private List<RecentViewEntity> recentViews;
}
```

#### ▶︎ RecentSearchEntity(최근 검색)

`RecentSearchEntity`의 `id`는 **복합키**로 구성하여 데이터의 **기본키**로 설정하고 유일성을 보장한다

동일한 키로 등록요청이 오면 업데이트 처리되고 없다면 등록 된다

부모참조 레퍼런스 키인 `user`는 User 데이터 삭제시 User에 해당 데이터도 모두 함께 삭제

```java
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "user")
@Entity
@Document(collection = "recent_search")
public class RecentSearchEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -5462945347689238006L;

    private LocalDate groupDate;
    private RecentSearch.Location location;
    private RecentSearch.Type searchType;
    private String nationCode;
    private String division;
    private String keyword;
    private String id;
    private String type;
    private String urId;
    private String options;
    private String from;
    private String to;
    private String code;
    private String data;
    private String name;
    private String parent;
    private String etc;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Indexed(name = "expiredAt", expireAfterSeconds = 0)
    private LocalDateTime expiredAt;

    @JsonIgnore
    @DBRef
    @ManyToOne
    private UserEntity user;

    @EmbeddedId
    private PrimaryKey primaryKey;

    public RecentSearchEntity setPrimaryKey(PrimaryKey primaryKey) {
        this.primaryKey = primaryKey.setRecentSearch(this);
        return this;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class PrimaryKey implements Serializable {
        private Site site;
        private RecentSearch.Location location;
        private RecentSearch.Type searchType;
        private IdType userType;
        private String userId;
        private String id;

        public PrimaryKey setId(String id) {
            this.id = id;
            return this;
        }

        public PrimaryKey setRecentSearch(RecentSearchEntity recentSearch) {
            this.location = recentSearch.getLocation();
            this.searchType = recentSearch.getSearchType();
            this.id = recentSearch.getId();
            return this;
        }
    }
}
```

#### ▶︎ RecentViewEntity(최근 조회)

`RecentViewEntity`의 `id`는 **복합키**로 구성하여 데이터의 **기본키**로 설정하고 유일성을 보장한다

동일한 키로 등록요청이 오면 업데이트 처리되고 없다면 등록 된다

부모참조 레퍼런스 키인 `user`는 User 데이터 삭제시 User에 해당 데이터도 모두 함께 삭제 된다

```java
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "user")
@Entity
@Document(collection = "recent_view")
public class RecentViewEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -4361245439171918852L;

    private LocalDate groupDate;
    private RecentView.Type viewType;
    private RecentView.LocaleType localeType;
    private RecentView.Location location;
    private String id;
    private String depDt;
    private String retDt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Indexed(name = "expiredAt", expireAfterSeconds = 0)
    private LocalDateTime expiredAt;

    @JsonIgnore
    @DBRef
    @ManyToOne
    private UserEntity user;

    @EmbeddedId
    private PrimaryKey primaryKey;

    public RecentViewEntity setPrimaryKey(PrimaryKey primaryKey) {
        this.primaryKey = primaryKey.setRecentView(this);
        return this;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class PrimaryKey {
        private Site site;
        private IdType userType;
        private String userId;
        private RecentView.Type viewType;
        private RecentView.LocaleType localeType;
        private RecentView.Location location;
        private String id;

        public PrimaryKey setRecentView(RecentViewEntity recentView) {
            this.viewType = recentView.getViewType();
            this.localeType = recentView.getLocaleType();
            this.location = recentView.getLocation();
            this.id = recentView.getId();
            return this;
        }
    }

}
```

---

## 🚦Spring Boot Data MongoDB Aggregation(`$lookup`) 예제

MongoDB는 통계나 조인 조회를 위해 Aggregation 기능을 제공하고 있으며 `$lookup` 을 사용하여 RDBMS 조인과 유사하게 참조하는 데이터를 조회할 수 있다

### 📌 MongoDB Aggregation `$match`와 `$lookup`을 사용한 Join Query 예제

- 참고
  - https://secretartbook.tistory.com/21
  - https://velog.io/@ddclub12/Webflux%EC%97%90%EC%84%9C-Mongo-Aggregation-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0
- 트러블슈팅: https://www.baeldung.com/spring-jpa-non-unique-result-exception
  - `$match` 쿼리 작성을 누락하여 다건의 데이터가 조회되어 오류가 발생

아래의 **Aggregation** 쿼리는 파라메터로 들어온 첫번째 값과 **match** 되는 `user` 데이터를 `$match`로 찾고
해당 `user` 를 참조하고 있는 `recent_search`와 `recent_view` 를 데이터를 `$lookup`으로 조회하는 예제이다

```java
public interface UserRepository extends MongoRepository<UserEntity, String>, QuerydslPredicateExecutor<UserEntity>, UserRepositoryWrapper {

    @Aggregation({"{$match: {_id: ?0}}"
            ,"{$lookup:  {from:  \"recent_search\", localField:  \"_id\", foreignField: \"_id.userId\",as: \"recentSearches\"}}"
            ,"{$lookup:  {from:  \"recent_view\", localField:  \"_id\", foreignField: \"_id.userId\",as: \"recentViews\"}}"})
    UserEntity findOneById(String id);
}
```

---

## 🚦Spring Boot DataMongoDB QueryDSL 및 Wrapper를 사용한 구성 예제

MongoRepository로 구성하기 애매한 Query의 경우 Wrapper 인터페이스에 Custom한 인터페이스를 정의하고 Impl 구현체를 생성하여 QueryDSL로 Query를 구현한다

### 📌 MongoDB QueryDSL 및 Wrapper 구성

- 참고
  - https://beanbroker.github.io/2020/08/16/Spring/mongodb_srping3/

`UserRepositoryWrapper`을 생성하고 Custom한 `findByUserId` Query 인터페이스를 정의 한다

```java
public interface UserRepositoryWrapper {

    UserEntity findByUserId(String id);
}
```

`QUserEntity.userEntity`를 사용하여 QueryDSL Query 로직을 작성하고 `@Repository` 를 추가 한다

```java
@Repository
public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryWrapper {

    public UserRepositoryImpl(MongoOperations operations) {
        super(operations);
    }

    @Override
    public UserEntity findByUserId(String id) {
        return from(userEntity).where(userEntity.id.eq(id)).fetchOne();
    }
}
```

---

## 🚦Spring Boot Data MongoDB Service 구현 예제

### 📌 Service 구현

#### ▶︎ UserService(유저 서비스) 구현
```java
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
```

#### ▶︎ RecentSearchMongoService(최근 검색 서비스) 구현

```java
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
```

#### ▶︎ RecentViewMongoService(최근 조회 서비스) 구현

```java
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
```


### 📌 `AbstractMongoEventListener` Lifecycle Event를 활용한 Cascade 삭제

MongoDB의 경우 RDBMS와 같이 참조된 부모 데이터가 삭제시 자식 데이터들을 조건으로 함께 삭제하는 기능이 기본적으로 제공되지 않기 때문에 `AbstractMongoEventListener` 인터페이스를 상속받아 Lifecycle Event를 활용해 Cascade 삭제 로직을 구현해야 한다

MongoDB 데이터 처리 타입에 맞게 Lifecycle Event 가 동작하므로 필요한 Event에 처리 로직을 구현하면 된다

#### ▶︎ Cascade 삭제 구현

- 참고
  - https://www.baeldung.com/cascading-with-dbref-and-lifecycle-events-in-spring-data-mongodb
  - https://docs.spring.io/spring-data/mongodb/reference/mongodb/lifecycle-events.html

```java
@Slf4j
@Component
@RequiredArgsConstructor
public class UserCascadeDeleteMongoEventListener extends AbstractMongoEventListener<UserEntity> {

    private final RecentSearchMongoService recentSearchMongoService;
    private final RecentViewMongoService recentViewMongoService;

    /**
     * 삭제후 호출
     * @param userEvent will never be {@literal null}.
     */
    @Override
    public void onAfterDelete(AfterDeleteEvent<UserEntity> userEvent) {
        String userId = userEvent.getSource().get("_id").toString();
        Integer deleteCountRecentSearch = recentSearchMongoService.deleteRecentSearchByUserId(userId);
        Integer deleteCountRecentVeiw = recentViewMongoService.deleteRecentViewByUserId(userId);
        log.info("onAfterDelete: {}, deleteCountRecentSearch: {}, deleteCountRecentView: {}",userId, deleteCountRecentSearch, deleteCountRecentVeiw);
        super.onAfterDelete(userEvent);
    }
}
```

---

## 🚦Spring Boot Data MongoDB Test 코드 작성

Sample 코드의 테스트 코드는 간단하게 **testcontainers** 를 활용해 테스트 해보았다
Docker 환경은 구성되어 있어야 하며 **testcontainers** 라이브러리가 자동으로 MongoDB Docker Container를 구성해 테스트를 할 수있게 도와준다

별도의 테스트용 MongoDB를 구성할 필요도 없이 자동으로 구성되어 테스트할 수 있으니 매우 편리한 것 같다

그외 실제 MongoDB 데이터를 조회하고 확인하면서 테스트하고 싶다면 MongoDB Atlas 무료 MongoDB를 구성해서 테스트해도 되고
별도의 Docker 컨테이너를 직접 구성해서 테스트 해봐도 된다

간단하게 테스트 해볼 수 있는 MongoDB Docker Compose 스크립트 이다

```yaml
version: '3.8'

services:
  mongodb:
    image: mongo
    ports:
      - "${MONGO_PORT:-27017}:27017"
    volumes:
      - .data:/data
    container_name: "docker-mongodb"
    environment:
      - MONGO_INITDB_ROOT_USERNAME=root
      - MONGO_INITDB_ROOT_PASSWORD=root
      - MONGO_INITDB_DATABASE=test
volumes:
  data: {}

networks:
  default:
    name: mongodb_network
```

MongoDB GUI 툴로는 현재 가장 많이 사용되고 있는 [MongoDB Compass](https://www.mongodb.com/try/download/compass) 가 있다

- 참고
  - https://www.sktenterprise.com/bizInsight/blogDetail/dev/2652
  - https://www.baeldung.com/spring-boot-built-in-testcontainers
  - https://github.com/testcontainers/testcontainers-java-spring-boot-quickstart

### 📌 Service 테스트 코드 작성

아래의 테스트 코드들은 Docker 환경만 구성되면 호환성 문제로 `testcontainers` 오류가 발생하긴 하지만 별다른 처리 없이 정상적으로 성공되는 테스트 코드들이다


#### ▶︎ UserMongoServiceTest(유저 서비스 테스트)

```java
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
```

#### ▶︎ RecentSearchMongoServiceTest(최근 검색 서비스 테스트)

```java
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
```

#### ▶︎ RecentViewMongoServiceTest(최근 조회 서비스 테스트)

```java
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
```