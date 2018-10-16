package com.nicolas.sasapi.controller.mapper;

import com.nicolas.sasapi.domain.FavoriteMovie;
import com.nicolas.sasapi.domainvalue.TmdbMovie;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieMapper {

    public static FavoriteMovie fromTmdb(TmdbMovie tmdbMovie) {
        return FavoriteMovie.builder()
                .imdbId(tmdbMovie.getImdbId())
                .adult(tmdbMovie.getAdult())
                .overview(tmdbMovie.getOverview())
                .popularity(tmdbMovie.getPopularity())
                .releaseDate(tmdbMovie.getReleaseDate())
                .title(tmdbMovie.getTitle())
                .voteAverage(tmdbMovie.getVoteAverage())
                .voteCount(tmdbMovie.getVoteCount())
                .build();
    }

    public static List<TmdbMovie> toTmdbList(List<FavoriteMovie> favoriteMovies) {
        return favoriteMovies.stream()
                .map(MovieMapper::toTmdb)
                .collect(Collectors.toList());
    }

    private static TmdbMovie toTmdb(FavoriteMovie favoriteMovie) {
        return TmdbMovie.builder()
                .adult(favoriteMovie.getAdult())
                .imdbId(favoriteMovie.getImdbId())
                .overview(favoriteMovie.getOverview())
                .popularity(favoriteMovie.getPopularity())
                .releaseDate(favoriteMovie.getReleaseDate())
                .title(favoriteMovie.getTitle())
                .voteAverage(favoriteMovie.getVoteAverage())
                .voteCount(favoriteMovie.getVoteCount())
                .build();
    }

}
