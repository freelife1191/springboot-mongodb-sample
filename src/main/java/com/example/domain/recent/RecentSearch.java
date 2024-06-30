package com.example.domain.recent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * Created by mskwon on 2023/12/09.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(title = "최근 검색")
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecentSearch {

    @Hidden
    @JsonIgnore
    private Site site;

    @Hidden
    @JsonIgnore
    @Schema(title = "버전", hidden = true)
    private String version;

    @Schema(title = "검색 위치", description = "air: 항공, hotel: 숙소 , toact: 투어&티켓, place: 플레이스, package: 패키지")
    private Location location;

    @Schema(title = "검색 타입", description = "air: 1, city: 2, hotel: 3, keyword: 4, category: 6, product: 7, place: 8, area: 9")
    private Type searchType;

    @Schema(title = "국가", example = "KR")
    private String nationCode;

    @Schema(title = "검색 구분", description = "ex) hotel")
    private String division;

    @Schema(title = "키워드명", description = "ex) 제주도 전체, 한국")
    private String keyword;

    @Schema(title = "키워드 ID")
    private String id;

    @Schema(title = "키워드 URID")
    private String urid;

    @Schema(title = "옵션", description = "숙소(객실 인원 ex) 2~2,3~10,4~11)")
    private String options; // 옵션: 숙소(인원 수 성인2, 소인1)

    @Schema(title = "시작일", description = "ex) 2024-12-28")
    @Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$", message="날짜 형식이 올바르지 않습니다. 날짜형식: yyyy-MM-dd 예) 2024-01-01")
    private String from;

    @Schema(title = "종료일", description = "ex) 2024-12-31")
    @Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$", message="날짜 형식이 올바르지 않습니다. 날짜형식: yyyy-MM-dd 예) 2024-01-01")
    private String to;

    @Schema(title = "코드", description = "ex) jejudo")
    private String code; // 코드 옵션

    @Schema(title = "데이터", description = "데이터 ex) {\"ICN\":\"서울(인천)\",\"NRT\":\"도쿄(나리타)\",\"LAX\":\"로스앤젤레스\"}")
    private String data; // 데이터 옵션

    @Schema(title = "이름 또는 표시 이름")
    private String name;

    @Schema(title = "부모 ID 또는 데이터")
    private String parent;

    @Schema(title = "키워드 타입")
    private String type;

    @Schema(title = "기타")
    private String etc;

    @Schema(title = "최근 검색 TTL", description = "만료까지 남은시간")
    private String ttl;

    @Schema(title = "최근 검색 만료일시", description = "만료될 일시")
    private LocalDateTime expiredAt;

    @Schema(title = "최근 검색 수정일시")
    private LocalDateTime updatedAt;

    public enum Location {
        COMM, HOTEL, TOACT, PLACE, AIR, PACKAGE
    }

    public enum Type {
        AIR, CITY, HOTEL, KEYWORD, CATEGORY, PRODUCT, PLACE, AREA
    }

}