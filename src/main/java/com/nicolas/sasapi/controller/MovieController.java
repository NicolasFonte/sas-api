package com.nicolas.sasapi.controller;

import static com.nicolas.sasapi.controller.mapper.MovieMapper.toTmdbList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.nicolas.sasapi.domainvalue.TmdbMovie;
import com.nicolas.sasapi.exception.TmdbClientException;
import com.nicolas.sasapi.service.TmdbService;
import com.nicolas.sasapi.service.MovieService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/movies", produces = APPLICATION_JSON_VALUE)
public class MovieController {

    private final TmdbService tmdbService;
    private final MovieService movieService;

    public MovieController(TmdbService tmdbService, MovieService movieService) {
        this.tmdbService = tmdbService;
        this.movieService = movieService;
    }

    @GetMapping
    public List<TmdbMovie> getTmdbMostPopulars() throws TmdbClientException {
        return tmdbService.getTmdbMostPopulars();
    }

    @GetMapping("/{name}")
    public List<TmdbMovie> searchTmdbMovie(@PathVariable("name") String movieName) throws TmdbClientException {
        return tmdbService.searchTmdbMovie(movieName);
    }

    @GetMapping("/most_favorites")
    public List<TmdbMovie> getMostFavorites(@RequestParam(required = false, defaultValue = "5") int limit) {
        return toTmdbList(movieService.findTopFavorites(limit));
    }


}
