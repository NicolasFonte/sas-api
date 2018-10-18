package com.nicolas.sasapi.domainvalue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbMovie {

    public TmdbMovie() {

    }

    @JsonProperty("id")
    private Long tmdbId;

    private String title;

    private String originalTitle;

    private BigDecimal popularity;

    private String releaseDate;

    private Boolean adult;

    private String overview;

    private String originalLanguage;

    private BigDecimal voteAverage;

    private Long voteCount;

}
