package com.nicolas.sasapi.controller.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import com.nicolas.sasapi.domain.FavoriteMovie;
import com.nicolas.sasapi.domainvalue.TmdbMovie;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class MovieMapperTest {

    @Test
    public void testEntitiesConvertedToDTO() throws Exception {
        FavoriteMovie favoriteMovie1 = new FavoriteMovie(1L, 4L, null, "First", BigDecimal.TEN, "05/10/2018", false,
                "overview1", BigDecimal.TEN, 10L);
        FavoriteMovie favoriteMovie2 = new FavoriteMovie(2L, 5L, null, "Second", BigDecimal.ONE, "06/10/2018", true,
                "overview2", BigDecimal.ONE, 20L);

        List<TmdbMovie> tmdbMovies = MovieMapper.toTmdbList(Arrays.asList(favoriteMovie1, favoriteMovie2));

        assertThat(tmdbMovies)
                .hasSize(2)
                .extracting("tmdbId", "title", "popularity", "releaseDate", "adult", "overview", "voteAverage",
                        "voteCount")
                .containsExactly(
                        tuple(4L, "First", BigDecimal.TEN, "05/10/2018", false, "overview1", BigDecimal.TEN, 10L),
                        tuple(5L, "Second", BigDecimal.ONE, "06/10/2018", true, "overview2", BigDecimal.ONE, 20L));

    }

    @Test
    public void testDTOConvertedToEntity() throws Exception {
        TmdbMovie tmdbMovie = new TmdbMovie(1L, "venom", BigDecimal.TEN, "05/12/2018", false, "overview",
                BigDecimal.TEN, 1L);

        FavoriteMovie favoriteMovie = MovieMapper.fromTmdb(tmdbMovie);

        assertThat(favoriteMovie)
                .extracting("tmdbId", "title", "popularity", "releaseDate", "adult", "overview", "voteAverage",
                        "voteCount")
                .containsExactly(1L, "venom", BigDecimal.TEN, "05/12/2018", false, "overview", BigDecimal.TEN, 1L);
    }

}