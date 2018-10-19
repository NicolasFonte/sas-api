package com.nicolas.sasapi.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.nicolas.sasapi.controller.responsehandler.CustomizedResponseEntityExceptionHandler;
import com.nicolas.sasapi.domain.FavoriteMovie;
import com.nicolas.sasapi.domainvalue.TmdbMovie;
import com.nicolas.sasapi.exception.TmdbClientException;
import com.nicolas.sasapi.service.MovieService;
import com.nicolas.sasapi.service.TmdbService;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieControllerTest {

    @Mock
    private TmdbService tmdbService;

    @Mock
    private MovieService movieService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MovieController movieController = new MovieController(tmdbService, movieService);
        mockMvc = standaloneSetup(movieController)
                .setControllerAdvice(new CustomizedResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void testAPITmdbMostPopularMoviesRetrieved() throws Exception {
        when(tmdbService.getTmdbMostPopularMovies()).thenReturn(sampleMoviesDTO());

        mockMvc.perform(get("/api/v1/movies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$[0].id", is(134)))
                .andExpect(jsonPath("$[0].title", is("Most Favorite")))
                .andExpect(jsonPath("$[0].overview", is("overview1")))
                .andExpect(jsonPath("$[0].popularity", is(10)))
                .andExpect(jsonPath("$[1].id", is(135)))
                .andExpect(jsonPath("$[1].title", is("Second Favorite")))
                .andExpect(jsonPath("$[1].overview", is("overview2")))
                .andExpect(jsonPath("$[1].popularity", is(1)));

        verify(tmdbService, only()).getTmdbMostPopularMovies();
    }

    @Test
    public void testAPITmdbMostPopularMoviesClientExceptionReturnsHttp500() throws Exception {
        when(tmdbService.getTmdbMostPopularMovies()).thenThrow(new TmdbClientException("ClientError"));

        mockMvc.perform(get("/api/v1/movies"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", is("Error fetching TMDB API. Reason: ClientError")));

        verify(tmdbService, only()).getTmdbMostPopularMovies();
    }

    @Test
    public void testAPISearchMoviesByName() throws Exception {
        when(tmdbService.searchTmdbMovie("MovieName")).thenReturn(sampleMoviesDTO());

        mockMvc.perform(get("/api/v1/movies/MovieName"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$[0].id", is(134)))
                .andExpect(jsonPath("$[0].title", is("Most Favorite")))
                .andExpect(jsonPath("$[0].overview", is("overview1")))
                .andExpect(jsonPath("$[0].popularity", is(10)))
                .andExpect(jsonPath("$[1].id", is(135)))
                .andExpect(jsonPath("$[1].title", is("Second Favorite")))
                .andExpect(jsonPath("$[1].overview", is("overview2")))
                .andExpect(jsonPath("$[1].popularity", is(1)));

        verify(tmdbService, only()).searchTmdbMovie("MovieName");
    }

    @Test
    public void testAPISearchMoviesByNameClientErrorReturnsHttp500() throws Exception {
        when(tmdbService.searchTmdbMovie("MovieName")).thenThrow(new TmdbClientException("ClientException"));

        mockMvc.perform(get("/api/v1/movies/MovieName"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", is("Error fetching TMDB API. Reason: ClientException")));

        verify(tmdbService, only()).searchTmdbMovie("MovieName");
    }

    @Test
    public void testAPIFindTopFavorites() throws Exception {
        when(movieService.findTopFavoriteMovies(2)).thenReturn(sampleMoviesDO());

        mockMvc.perform(get("/api/v1/movies/top_favorite?limit=2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$[0].id", is(134)))
                .andExpect(jsonPath("$[0].title", is("Most Favorite")))
                .andExpect(jsonPath("$[0].overview", is("overview1")))
                .andExpect(jsonPath("$[0].popularity", is(10)))
                .andExpect(jsonPath("$[1].id", is(135)))
                .andExpect(jsonPath("$[1].title", is("Second Favorite")))
                .andExpect(jsonPath("$[1].overview", is("overview2")))
                .andExpect(jsonPath("$[1].popularity", is(1)));

        verify(movieService, only()).findTopFavoriteMovies(2);
    }

    @Test
    public void testAPIFindTopFavoritesWhenLimitInvalid() throws Exception {
        mockMvc.perform(get("/api/v1/movies/top_favorite?limit=-2"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", is("Limit should be positive")));

        verify(movieService, never()).findTopFavoriteMovies(-2);
    }

    private List<TmdbMovie> sampleMoviesDTO() {
        TmdbMovie mostFavorited = new TmdbMovie();
        mostFavorited.setTmdbId(134L);
        mostFavorited.setTitle("Most Favorite");
        mostFavorited.setOverview("overview1");
        mostFavorited.setPopularity(BigDecimal.TEN);

        TmdbMovie secondFavorited = new TmdbMovie();
        secondFavorited.setTmdbId(135L);
        secondFavorited.setTitle("Second Favorite");
        secondFavorited.setOverview("overview2");
        secondFavorited.setPopularity(BigDecimal.ONE);

        return Arrays.asList(mostFavorited, secondFavorited);
    }

    private List<FavoriteMovie> sampleMoviesDO() {
        FavoriteMovie mostFavorited = new FavoriteMovie();
        mostFavorited.setTmdbId(134L);
        mostFavorited.setTitle("Most Favorite");
        mostFavorited.setOverview("overview1");
        mostFavorited.setPopularity(BigDecimal.TEN);

        FavoriteMovie secondFavorited = new FavoriteMovie();
        secondFavorited.setTmdbId(135L);
        secondFavorited.setTitle("Second Favorite");
        secondFavorited.setOverview("overview2");
        secondFavorited.setPopularity(BigDecimal.ONE);

        return Arrays.asList(mostFavorited, secondFavorited);
    }

}