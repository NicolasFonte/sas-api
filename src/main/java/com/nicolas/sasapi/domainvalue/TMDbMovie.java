package com.nicolas.sasapi.domainvalue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TMDbMovie {

    private Long id;

    private String title;

    private String originalTitle;

    private Float popularity;

    private String releaseDate;

    private Boolean adult;

    private String overview;

    private String originalLanguage;

    private Float voteAverage;

    private Long voteCount;

}
