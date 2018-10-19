package com.nicolas.sasapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import com.nicolas.sasapi.domain.FavoriteMovie;
import com.nicolas.sasapi.repository.MovieRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    private MovieService movieService;

    @Before
    public void setUp() {
        movieService = new MovieService(movieRepository);
    }

    @Test
    public void testFindTopFavoritesLimited() throws Exception {
        Mockito.when(movieRepository.findTopFavoriteMovies()).thenReturn(sampleFavoritesOrdered());

        List<FavoriteMovie> topFavorite = movieService.findTopFavoriteMovies(1);

        assertThat(topFavorite)
                .extracting("id", "tmdbId", "title")
                .containsExactly(tuple(1L, 134L, "Most Favorite"));
    }


    private List<FavoriteMovie> sampleFavoritesOrdered() {
        FavoriteMovie mostFavorited = new FavoriteMovie();
        mostFavorited.setId(1L);
        mostFavorited.setTmdbId(134L);
        mostFavorited.setTitle("Most Favorite");

        FavoriteMovie secondMostFavorited = new FavoriteMovie();
        secondMostFavorited.setId(2L);
        secondMostFavorited.setTmdbId(135L);
        secondMostFavorited.setTitle("Second Favorite");

        FavoriteMovie thirdMostFavorited = new FavoriteMovie();
        thirdMostFavorited.setId(3L);
        thirdMostFavorited.setTmdbId(136L);
        thirdMostFavorited.setTitle("Third Favorite");

        return Arrays.asList(mostFavorited, secondMostFavorited, thirdMostFavorited);
    }

}