package com.nicolas.sasapi.service;

import com.nicolas.sasapi.domain.FavoriteMovie;
import com.nicolas.sasapi.domain.User;
import com.nicolas.sasapi.exception.ImdbExistsException;
import com.nicolas.sasapi.repository.UserRepository;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addFavoriteMovie(Long userId, FavoriteMovie favoriteMovie) throws ImdbExistsException {
        User user = userRepository.findById(userId).get();
        boolean imdbExists = user.getFavorites()
                .stream()
                .anyMatch(fav -> fav.getImdbId().equals(favoriteMovie.getImdbId()));
        if (imdbExists) {
            throw new ImdbExistsException();
        }


        user.addFavorite(favoriteMovie);

        return userRepository.save(user);
    }

    public List<FavoriteMovie> getUserFavorites(Long userId) {
        User user = userRepository.findById(userId).get();
        return user.getFavorites();
    }
}
