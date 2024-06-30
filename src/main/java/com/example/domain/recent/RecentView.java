package com.example.domain.recent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * Created by mskwon on 2/22/24.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(title = "최근 조회")
@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RecentView implements Cloneable {

    @Hidden
    @JsonIgnore
    private Site site;

    @Schema(title = "조회 위치")
    private Location location;

    @Schema(title = "조회 타입")
    private Type viewType;

    @Schema(title = "조회 아이디")
    private String id;

    @Schema(title = "가는날")
    private String depDt;

    @Schema(title = "오는날")
    private String retDt;

    /**
     * 히든
     */
    @Schema(title = "국내(DOM)/해외(INT) 구분")
    private LocaleType localeType;

    @Hidden
    @JsonIgnore
    @Schema(title = "만료여부(true: 만료, false: 만료아님)")
    private boolean expired;

    @Schema(title = "최근 조회 TTL", description = "만료까지 남은시간")
    private String ttl;

    @Schema(title = "만료일시(만료될 일시)")
    private LocalDateTime expiredAt;

    @Schema(title = "수정일시")
    private LocalDateTime updatedAt;

    @Schema(title = "최근 검색 데이터", description = "검색에서 조회로 변경한 데이터의 경우 검색 데이터도 제공")
    private RecentSearch recentSearch;

    @Override
    public RecentView clone() {
        try {
            return (RecentView) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public enum LocaleType {
        DOM, INT
    }

    public enum Location {
        COMM, HOTEL, TOACT, PLACE, AIR, PACKAGE
    }

    public enum Type {
        CITY, HOTEL, TOACT, AIRPORT, PACKAGE
    }

}
