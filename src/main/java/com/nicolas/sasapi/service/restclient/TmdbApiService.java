package com.nicolas.sasapi.service.restclient;

import com.nicolas.sasapi.domainvalue.TmdbMovie;
import com.nicolas.sasapi.service.restclient.bean.TmdbResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TmdbApiService {

    /**
     * Gets most popular movies from TMDB api
     *
     * @param apiKey - user api key to validate
     * @return popular movies from given page
     */
    @GET("/3/movie/popular")
    Call<TmdbResponse> getMostPopular(@Query("api_key") String apiKey);


    /**
     * Search Movies from name.
     * It considers all original, translated, alternative names and titles.
     *
     * @param query - text ot seach
     * @param apiKey - user api key to validate
     * @return list of movies searched by name
     */
    @GET("/3/search/movie")
    Call<TmdbResponse> searchByName(@Query("query") String query, @Query("api_key") String apiKey);

    // /movie/{movie_id}

    /**
     * Get single movie by its Tmdb Id
     *
     * @param tmdbId - movie id to find
     * @param apiKey - user api key to validate
     * @return movie from given tmdb id
     */
    @GET("/3/movie/{movie_id}")
    Call<TmdbMovie> findByTmdbId(@Path("movie_id") Long tmdbId, @Query("api_key") String apiKey);

}
