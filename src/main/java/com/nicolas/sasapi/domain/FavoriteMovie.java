package com.nicolas.sasapi.domain;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity(name = "favorite")
@Builder
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = {"id", "imdbId"})
public class FavoriteMovie {

    public FavoriteMovie() {

    }

    @Id
    @GeneratedValue
    private Long id;

    private Long imdbId;

    @ManyToMany(mappedBy = "favorites")
    private List<User> favoritedByUsers;

    private String title;

    private BigDecimal popularity;

    private String releaseDate;

    private Boolean adult;

    private String overview;

    private BigDecimal voteAverage;

    private Long voteCount;

    @Override
    public String toString() {
        return id + "|" + imdbId + "|" + title;
    }

}
