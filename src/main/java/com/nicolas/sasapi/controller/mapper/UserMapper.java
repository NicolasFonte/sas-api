package com.nicolas.sasapi.controller.mapper;

import com.nicolas.sasapi.domain.User;
import com.nicolas.sasapi.controller.response.UserResponse;

public class UserMapper {

    public static UserResponse toDTO(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setName(user.getName());
        userResponse.setFavoriteMovies(MovieMapper.toTmdbList(user.getFavorites()));

        return userResponse;
    }
}
