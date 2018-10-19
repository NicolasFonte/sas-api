package com.nicolas.sasapi.controller;

import static com.nicolas.sasapi.controller.mapper.MovieMapper.fromTmdb;
import static com.nicolas.sasapi.controller.mapper.MovieMapper.toTmdbList;
import static com.nicolas.sasapi.controller.mapper.UserMapper.toDTO;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.nicolas.sasapi.controller.bean.TmdbIdRequest;
import com.nicolas.sasapi.controller.bean.UserResponse;
import com.nicolas.sasapi.domain.FavoriteMovie;
import com.nicolas.sasapi.domain.User;
import com.nicolas.sasapi.domainvalue.TmdbMovie;
import com.nicolas.sasapi.exception.TmdbClientException;
import com.nicolas.sasapi.exception.TmdbIdExistsException;
import com.nicolas.sasapi.exception.UserNotFoundException;
import com.nicolas.sasapi.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/users", produces = APPLICATION_JSON_VALUE)
@Api(description = "User Endpoints. Allows to mark favorite movies and see user favorite ones")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}/favorites")
    @ApiOperation("Get movies marked as favorite from given user.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User favorite movies could be retrieved"),
            @ApiResponse(code = 404, message = "User not found")
    })
    public List<TmdbMovie> findUserFavoriteMovies(@PathVariable Long userId) throws UserNotFoundException {
        List<FavoriteMovie> userMovies = userService.findUserFavoriteMovies(userId);
        return toTmdbList(userMovies);
    }

    @PostMapping("/{userId}/favorites")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Post a movie as favorite by Tmdb Id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Favorite movie created and added to user"),
            @ApiResponse(code = 200, message = "User already selected this movie. Nothing changed."),
            @ApiResponse(code = 400, message = "Json unable to be parsed"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 422, message = "Json fields could not be properly parsed"),
    })
    public UserResponse markFavorite(@PathVariable Long userId, @RequestBody TmdbIdRequest tmdbIdRequest)
            throws TmdbIdExistsException, UserNotFoundException, TmdbClientException {
        User user = userService.addFavoriteMovie(userId, tmdbIdRequest.getTmdbId());
        return toDTO(user);
    }
}