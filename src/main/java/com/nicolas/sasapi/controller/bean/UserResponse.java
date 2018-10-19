package com.nicolas.sasapi.controller.bean;

import com.nicolas.sasapi.domainvalue.TmdbMovie;
import java.util.List;
import lombok.Data;

@Data
public class UserResponse {

    private String name;

    private List<TmdbMovie> favoriteMovies;

}
