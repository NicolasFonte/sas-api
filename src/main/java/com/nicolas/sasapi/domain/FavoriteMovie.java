package com.nicolas.sasapi.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "favorite")
@Builder
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "tmdbId"})
public class FavoriteMovie {

    public FavoriteMovie() {

    }

    @Id
    @GeneratedValue(generator = "LOCATION_SEQ_GEN", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequenceName = "location_sequence_name", name = "LOCATION_SEQ_GEN", initialValue = 4)
    private Long id;

    @Column(unique = true)
    private Long tmdbId;

    @ManyToMany(mappedBy = "favorites")
    private List<User> favoritedByUsers = new ArrayList<>();

    private String title;

    private BigDecimal popularity;

    private String releaseDate;

    private Boolean adult;

    private String overview;

    private BigDecimal voteAverage;

    private Long voteCount;

    @Override
    public String toString() {
        return id + "|" + tmdbId + "|" + title;
    }

}
