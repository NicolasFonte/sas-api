package com.nicolas.sasapi.service.restclient;

import com.nicolas.sasapi.service.restclient.bean.TMDbResponseBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;

@Service
public class OkHttpTMDbClientService implements TMDbClientService {

    private final String tmdbUrl;

    public OkHttpTMDbClientService(@Value("${api.tmdb.url}") String tmdbUrl) {
        this.tmdbUrl = tmdbUrl;
    }

    @Override
    public Call<TMDbResponseBean> getMostPopulars(String apiKey) {
        TMDbClientService tmdbService = TMDbClientService.buildTmdbClientService(tmdbUrl);
        return tmdbService.getMostPopulars(apiKey);
    }

    @Override
    public Call<TMDbResponseBean> searchByName(String query, String apiKey) {
        TMDbClientService tmdbService = TMDbClientService.buildTmdbClientService(tmdbUrl);
        return tmdbService.searchByName(query, apiKey);
    }

}
