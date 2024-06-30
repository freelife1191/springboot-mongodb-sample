package com.example.domain.recent.entity;

import com.example.domain.recent.IdType;
import com.example.domain.recent.RecentSearch;
import com.example.domain.recent.Site;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by mskwon on 4/29/24.
 */
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
