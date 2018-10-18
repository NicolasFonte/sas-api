package com.nicolas.sasapi.service;

import com.nicolas.sasapi.domain.FavoriteMovie;
import com.nicolas.sasapi.repository.MovieRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<FavoriteMovie> findTopFavoriteMovies(int limit) {
        List<FavoriteMovie> mostFavorites = movieRepository.findTopFavoriteMovies();
        return mostFavorites.stream()
                .limit(limit)
                .collect(Collectors.toList());

    }
}
