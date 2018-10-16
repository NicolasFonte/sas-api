package com.nicolas.sasapi.repository;

import com.nicolas.sasapi.domain.FavoriteMovie;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<FavoriteMovie, Long> {

    @Query("SELECT f FROM favorite f INNER JOIN f.favoritedByUsers GROUP BY f.id ORDER BY COUNT(f.id) DESC")
    List<FavoriteMovie> getMostFavorites();
}
