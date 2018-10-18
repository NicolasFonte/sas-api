package com.nicolas.sasapi.repository;

import com.nicolas.sasapi.domain.FavoriteMovie;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<FavoriteMovie, Long> {

    Optional<FavoriteMovie> findByTmdbId(Long tmdbId);

    @Query("SELECT f FROM favorite f INNER JOIN f.favoritedByUsers GROUP BY f.id ORDER BY COUNT(f.id) DESC")
    List<FavoriteMovie> findTopFavoriteMovies();
}
