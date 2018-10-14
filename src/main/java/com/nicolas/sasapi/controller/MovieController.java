package com.nicolas.sasapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.nicolas.sasapi.domainvalue.TMDbMovie;
import com.nicolas.sasapi.exception.TmdbClientException;
import com.nicolas.sasapi.service.MovieService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/movies", produces = APPLICATION_JSON_VALUE)
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<TMDbMovie> getMostPopulars() throws TmdbClientException {
        return movieService.findMostPopulars();
    }

    @GetMapping("/{name}")
    public List<TMDbMovie> getByName(@PathVariable("name") String movieName) throws TmdbClientException {
        return movieService.searchByName(movieName);
    }
}
