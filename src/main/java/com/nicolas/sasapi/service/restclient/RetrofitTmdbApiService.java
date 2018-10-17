package com.nicolas.sasapi.service.restclient;

import com.nicolas.sasapi.service.restclient.bean.TmdbResponse;
import org.springframework.stereotype.Service;
import retrofit2.Call;

@Service
public class RetrofitTmdbApiService implements TmdbApiService {

    private final TmdbApiServiceBuilder tmdbApiServiceBuilder;

    public RetrofitTmdbApiService(TmdbApiServiceBuilder tmdbApiServiceBuilder) {
        this.tmdbApiServiceBuilder = tmdbApiServiceBuilder;
    }

    @Override
    public Call<TmdbResponse> getMostPopular(String apiKey) {
        TmdbApiService tmdbApiService = tmdbApiServiceBuilder.buildTmdbApiService();
        return tmdbApiService.getMostPopular(apiKey);
    }

    @Override
    public Call<TmdbResponse> searchByName(String query, String apiKey) {
        TmdbApiService tmdbApiService = tmdbApiServiceBuilder.buildTmdbApiService();
        return tmdbApiService.searchByName(query, apiKey);
    }

}
