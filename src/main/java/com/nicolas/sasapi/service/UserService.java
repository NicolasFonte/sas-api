package com.nicolas.sasapi.service;

import static com.nicolas.sasapi.controller.mapper.MovieMapper.fromTmdb;

import com.nicolas.sasapi.domain.FavoriteMovie;
import com.nicolas.sasapi.domain.User;
import com.nicolas.sasapi.exception.TmdbClientException;
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

    private final TmdbService tmdbService;

    public UserService(UserRepository userRepository, MovieRepository movieRepository,
            TmdbService tmdbService) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.tmdbService = tmdbService;
    }

    public List<FavoriteMovie> findUserFavoriteMovies(Long userId) throws UserNotFoundException {
        User user = findUser(userId);
        return user.getFavorites();
    }

    public User addFavoriteMovie(Long userId, Long tmdbId)
            throws TmdbIdExistsException, UserNotFoundException, TmdbClientException {
        User user = findUser(userId);
        checkMovieMarked(user, tmdbId);
        FavoriteMovie movieToStore = findMovieToStore(tmdbId);
        user.addFavorite(movieToStore);

        return userRepository.save(user);
    }

    private FavoriteMovie findMovieToStore(Long tmdbId) throws TmdbClientException {
        FavoriteMovie movieToStore = movieRepository.findByTmdbId(tmdbId).orElse(null);
        if (movieToStore == null) {
            log.info("Movie id {} not exists in out system. Fetching from TMDB");
            movieToStore = fromTmdb(tmdbService.findByTmdbId(tmdbId));
        }
        return movieToStore;
    }

    private void checkMovieMarked(User user, Long tmdbId) throws TmdbIdExistsException {
        boolean tmdbExists = user.getFavorites().stream()
                .anyMatch(fav -> fav.getTmdbId().equals(tmdbId));
        if (tmdbExists) {
            log.info("Movie already selected as favorite");
            throw new TmdbIdExistsException(tmdbId);
        }
    }

    private User findUser(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User {} not found", userId);
                    return new UserNotFoundException(userId);
                });
    }

}
