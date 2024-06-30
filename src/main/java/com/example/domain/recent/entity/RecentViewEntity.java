package com.example.domain.recent.entity;

import com.example.domain.recent.IdType;
import com.example.domain.recent.RecentView;
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
