package com.nicolas.sasapi;

import com.nicolas.sasapi.domain.FavoriteMovie;
import com.nicolas.sasapi.domain.User;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestDataProvider {

    public static User createUser(String name) {
        User user = new User();
        user.setId(10L);
        user.setName(name);
        List<FavoriteMovie> favoriteMovies = Arrays.asList(createFavorite(10L, 20L, "name1", BigDecimal.TEN),
                createFavorite(30L, 40L, "name2", BigDecimal.ONE));
        // above fixed sizing
        user.setFavorites(new ArrayList<>(favoriteMovies));

        return user;
    }

    public static FavoriteMovie createFavorite(Long id, Long tmdbId, String title, BigDecimal popularity) {
        FavoriteMovie favoriteMovie = new FavoriteMovie();
        favoriteMovie.setId(id);
        favoriteMovie.setTmdbId(tmdbId);
        favoriteMovie.setTitle(title);
        favoriteMovie.setPopularity(popularity);

        return favoriteMovie;
    }
}
