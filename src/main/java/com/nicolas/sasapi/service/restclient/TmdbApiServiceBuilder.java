package com.nicolas.sasapi.service.restclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Component
public class TmdbApiServiceBuilder {

    private final String tmdbUrl;

    public TmdbApiServiceBuilder(@Value("${api.tmdb.url}") String tmdbUrl) {
        this.tmdbUrl = tmdbUrl;
    }

    TmdbApiService buildTmdbApiService() {
        Builder httpClient = getOkHttpClientBuilder();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        return new Retrofit.Builder()
                .baseUrl(tmdbUrl)
                .client(httpClient.build())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build()
                .create(TmdbApiService.class);
    }

    private static Builder getOkHttpClientBuilder() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(Level.HEADERS);

        Builder httpClient = new Builder();
        httpClient.addInterceptor(logging);

        return httpClient;
    }
}
