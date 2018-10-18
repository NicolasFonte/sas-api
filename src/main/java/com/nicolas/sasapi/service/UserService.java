package com.nicolas.sasapi.service;

import com.nicolas.sasapi.domain.FavoriteMovie;
import com.nicolas.sasapi.domain.User;
import com.nicolas.sasapi.exception.TmdbIdExistsException;
import com.nicolas.sasapi.exception.UserNotFoundException;
import com.nicolas.sasapi.repository.MovieRepository;
import com.nicolas.sasapi.repository.UserRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final MovieRepository movieRepository;

    public UserService(UserRepository userRepository, MovieRepository movieRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    public User addFavoriteMovie(Long userId, FavoriteMovie favoriteMovie)
            throws TmdbIdExistsException, UserNotFoundException {
        User user = findUser(userId);
        checkMovieMarked(user, favoriteMovie);

        FavoriteMovie movieToStore = movieRepository.findByTmdbId(favoriteMovie.getTmdbId()).orElse(favoriteMovie);

        // update dynamic data from tmdb
        movieToStore.setPopularity(favoriteMovie.getPopularity());
        movieToStore.setVoteAverage(favoriteMovie.getVoteAverage());
        movieToStore.setVoteCount(favoriteMovie.getVoteCount());
        user.addFavorite(movieToStore);
        return userRepository.save(user);

    }

    private void checkMovieMarked(User user, FavoriteMovie favoriteMovie) throws TmdbIdExistsException {
        boolean tmdbExists = user.getFavorites().stream()
                .anyMatch(fav -> fav.getTmdbId().equals(favoriteMovie.getTmdbId()));
        if (tmdbExists) {
            log.info("Movie already selected as favorite");
            throw new TmdbIdExistsException(favoriteMovie.getTmdbId());
        }
    }

    private User findUser(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User {} not found", userId);
                    return new UserNotFoundException(userId);
                });
    }

    public List<FavoriteMovie> findUserFavoriteMovies(Long userId) throws UserNotFoundException {
        User user = findUser(userId);
        return user.getFavorites();
    }
}
