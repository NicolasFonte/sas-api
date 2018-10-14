package com.nicolas.sasapi.service.restclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.nicolas.sasapi.service.restclient.bean.TMDbResponseBean;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TMDbClientService {

    /**
     * Gets most popular movies from TMDB api
     *
     * @param apiKey - user api key to validate
     * @return popular movies from given page
     */
    @GET("/3/movie/popular")
    Call<TMDbResponseBean> getMostPopulars(@Query("api_key") String apiKey);


    /**
     * Search Movies from name.
     * It considers all original, translated, alternative names and titles.
     *
     * @param query - text ot seach
     * @param apiKey - user api key to validate
     * @return list of movies searched by name
     */
    @GET("/3/search/movie")
    Call<TMDbResponseBean> searchByName(@Query("query") String query, @Query("api_key") String apiKey);

    static TMDbClientService buildTmdbClientService(String tmdbUrl) {
        Builder httpClient = getOkHttpClientBuilder();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        return new Retrofit.Builder()
                .baseUrl(tmdbUrl)
                .client(httpClient.build())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build()
                .create(TMDbClientService.class);
    }

    static Builder getOkHttpClientBuilder() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(Level.HEADERS);

        Builder httpClient = new Builder();
        httpClient.addInterceptor(logging);

        return httpClient;
    }
}
