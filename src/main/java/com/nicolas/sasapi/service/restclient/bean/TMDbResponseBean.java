package com.nicolas.sasapi.service.restclient.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nicolas.sasapi.domainvalue.TMDbMovie;
import java.util.List;
import lombok.Getter;

@Getter
public class TMDbResponseBean {

    private Long page;

    private Long totalResults;

    private Long totalPages;

    @JsonProperty("results")
    private List<TMDbMovie> tmDbMovies;
}
