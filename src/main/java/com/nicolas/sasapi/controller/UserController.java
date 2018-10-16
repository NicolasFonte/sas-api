package com.nicolas.sasapi.controller;

import static com.nicolas.sasapi.controller.mapper.MovieMapper.*;
import static com.nicolas.sasapi.controller.mapper.MovieMapper.fromTmdb;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.nicolas.sasapi.domain.FavoriteMovie;
import com.nicolas.sasapi.domain.User;
import com.nicolas.sasapi.domainvalue.TmdbMovie;
import com.nicolas.sasapi.exception.ImdbExistsException;
import com.nicolas.sasapi.service.UserService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/users", produces = APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{userId}/favorite")
    public User markFavorite(@PathVariable Long userId, @RequestBody TmdbMovie tmdbMovie) throws ImdbExistsException {
        return userService.addFavoriteMovie(userId, fromTmdb(tmdbMovie));
    }

    @GetMapping("/{userId}/favorites")
    public List<TmdbMovie> getUserFavorites(@PathVariable Long userId) {
        List<FavoriteMovie> userFavorites = userService.getUserFavorites(userId);
        return toTmdbList(userFavorites);
    }
}