package com.nicolas.sasapi.service.restclient.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nicolas.sasapi.domainvalue.TmdbMovie;
import java.util.List;
import lombok.Getter;

@Getter
public class TmdbResponse {

    private Long page;

    private Long totalResults;

    private Long totalPages;

    @JsonProperty("results")
    private List<TmdbMovie> tmdbMovies;
}
