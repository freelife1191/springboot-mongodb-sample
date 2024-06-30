package com.example.domain.recent.entity;

import com.example.domain.recent.IdType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by mskwon on 4/29/24.
 */
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
