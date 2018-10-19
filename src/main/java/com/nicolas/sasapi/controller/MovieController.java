package com.nicolas.sasapi.controller;

import static com.nicolas.sasapi.controller.mapper.MovieMapper.toTmdbList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.nicolas.sasapi.domainvalue.TmdbMovie;
import com.nicolas.sasapi.exception.InvalidLimitException;
import com.nicolas.sasapi.exception.TmdbClientException;
import com.nicolas.sasapi.service.TmdbService;
import com.nicolas.sasapi.service.MovieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/movies", produces = APPLICATION_JSON_VALUE)
@Api(description = "Movie endpoints. Allows to get popular movies, search and see favorites.")
public class MovieController {

    private final TmdbService tmdbService;
    private final MovieService movieService;

    public MovieController(TmdbService tmdbService, MovieService movieService) {
        this.tmdbService = tmdbService;
        this.movieService = movieService;
    }

    @GetMapping
    @ApiOperation("Retrieve most popular movies according TMDB.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Popular Movies retrieved successfully"),
            @ApiResponse(code = 500, message = "Error fetching TMDB APi")})
    public List<TmdbMovie> getTmdbMostPopularMovies() throws TmdbClientException {
        return tmdbService.getTmdbMostPopularMovies();
    }

    @GetMapping("/{query}")
    @ApiOperation("Search TMDB movie by its name.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Popular Movies retrieved successfully"),
            @ApiResponse(code = 500, message = "Error fetching TMDB APi")})
    public List<TmdbMovie> searchTmdbMovie(@PathVariable("query") String movieName) throws TmdbClientException {
        return tmdbService.searchTmdbMovie(movieName);
    }

    @GetMapping("/top_favorite")
    @ApiOperation("find most favorite movies according SAS Movie App.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Most Favorite movies retrieved"),
            @ApiResponse(code = 400, message = "Limit is negative")})
    public List<TmdbMovie> findTopFavoriteMovies(
            @RequestParam(required = false, defaultValue = "1") int limit) throws InvalidLimitException {
        if (limit < 0) {
            throw new InvalidLimitException();
        }
        return toTmdbList(movieService.findTopFavoriteMovies(limit));
    }


}
