# Document DB 개발환경 셋팅

<!-- TOC -->
* [Document DB 개발환경 셋팅](#document-db-개발환경-셋팅)
  * [🚦 개발 환경 설정](#-개발-환경-설정)
    * [Mongo Shell 접속 명렁어](#mongo-shell-접속-명렁어)
    * [SSM Connection 명령어](#ssm-connection-명령어)
    * [Application 접속 URI](#application-접속-uri)
    * [DocumentDB Local 접속 Alias 설정](#documentdb-local-접속-alias-설정)
  * [🚦 MongoDB Compass로 DocumentDB 접속](#-mongodb-compass로-documentdb-접속)
  * [MongoDB Compass Aggregations 탭에서 Aggregation 테스트](#mongodb-compass-aggregations-탭에서-aggregation-테스트)
<!-- TOC -->

Sample Code를 작성하기 전에 AWS DocumentDB 로 개발환경을 셋팅해서 진행했던 내용을 정리

AWS Document DB의 경우 기본적으로 Private 도메인만 제공되므로 로컬에서는 AWS SSM을 통해 port forwarding 하여 접속

- 참고
    - https://findstar.pe.kr/2022/09/03/accessing-private-rds-instance-using-ssm/

---

## 🚦 개발 환경 설정

### Mongo Shell 접속 명렁어

```shell
$ mongosh --host test-dev-docdb.cluster-cueokyhf6yae.ap-northeast-2.docdb.amazonaws.com:27017 --username username --password password
```

### SSM Connection 명령어

```shell
$ aws ssm start-session --target i-07609be66e5800000 \
--document-name AWS-StartPortForwardingSessionToRemoteHost \
--parameters '{"portNumber":["27017"],"localPortNumber":["27017"],"host":["test-dev-docdb.cluster-cueokyhf6yae.ap-northeast-2.docdb.amazonaws.com"]}'
```

### Application 접속 URI

```shell
mongodb://username:password@localhost:27017/database?directConnection=true&replicaSet=rs0&readPreference=secondaryPreferred&retryWrites=false&w=majority
```

### DocumentDB Local 접속 Alias 설정

먼저 Local에 `aws-session-manager-plugin` 가 설치 되어 있어야 된다

```shell
# Local에 aws-session-manager-plugin 설치
brew install aws-session-manager-plugin
```

DocumentDB Local 접속 Alias를 `.zshrc`에 추가

```shell
alias docdb="aws ssm start-session --target i-07609be66e5800000 \
--document-name AWS-StartPortForwardingSessionToRemoteHost \
--parameters '{\"portNumber\":[\"27017\"],\"localPortNumber\":[\"27017\"],\"host\":[\"test-dev-docdb.cluster-cueokyhf6yae.ap-northeast-2.docdb.amazonaws.com\"]}'"
```

이렇게 나오면 Local에서 DocumentDB에 접속할 준비가 완료된 상태

![img.png](attachments/img1.png)

---

## 🚦 MongoDB Compass로 DocumentDB 접속

DocumentDB 개발 환경 설정을 마치고 Local에서 DocumentDB 접속할 준비가 된 상태에서

접속 URI를 입력하거나 Advanced Connection Options를 설정하고 Connect 버튼을 클릭하여 접속

`mongodb://username:password@localhost:27017/?directConnection=true&authSource=database`

![img.png](attachments/img2.png)

---

## MongoDB Compass Aggregations 탭에서 Aggregation 테스트

- 참고
  - https://secretartbook.tistory.com/21

`$match`, `$lookup` 을 사용한 Pipeline Query를 구성해서 user와 연관된 recent_search 와 recent_vew 데이터를 Aggregation 해서 가져오는 Sample

코드로 작성하기 전에 MongoDB Compass의 Aggregation 탭에서 Query를 먼저 테스트해보고 코드를 작성하는 것이 좋다

![img.png](attachments/img3.png)