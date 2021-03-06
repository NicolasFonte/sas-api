package com.nicolas.sasapi.service;

import com.nicolas.sasapi.domainvalue.TmdbMovie;
import com.nicolas.sasapi.exception.TmdbClientException;
import com.nicolas.sasapi.service.restclient.TmdbApiService;
import com.nicolas.sasapi.service.restclient.bean.TmdbResponse;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Slf4j
@Service
public class TmdbService {

    private final String apiKey;

    private final TmdbApiService tmdbApiService;

    public TmdbService(@Value("${api.tmdb.key}") String apiKey, TmdbApiService tmdbApiService) {
        this.apiKey = apiKey;
        this.tmdbApiService = tmdbApiService;
    }

    public List<TmdbMovie> getTmdbMostPopularMovies() throws TmdbClientException {
        try {
            Response<TmdbResponse> tmdbResponse = tmdbApiService.getMostPopular(apiKey).execute();
            TmdbResponse tmdbResponseBean = fetchResponse(tmdbResponse);
            return tmdbResponseBean.getTmdbMovies();
        } catch (IOException ex) {
            log.error("TMDB API was not reached. Probably down.", ex);
            throw new TmdbClientException(ex.getMessage());
        }
    }

    public List<TmdbMovie> searchTmdbMovie(String name) throws TmdbClientException {
        try {
            Response<TmdbResponse> tmdbApiResponse = tmdbApiService.searchByName(name, apiKey).execute();
            TmdbResponse tmdbResponseBean = fetchResponse(tmdbApiResponse);
            return tmdbResponseBean.getTmdbMovies();
        } catch (IOException ex) {
            log.error("TMDB API was not reached. Probably down.", ex);
            throw new TmdbClientException(ex.getMessage());
        }
    }

    TmdbMovie findByTmdbId(Long imdbId) throws TmdbClientException {
        try {
            Response<TmdbMovie> tmdbApiResponse = tmdbApiService.findByTmdbId(imdbId, apiKey).execute();
            return fetchResponse(tmdbApiResponse);
        } catch (IOException ex) {
            log.error("TMDB API was not reached. Probably down.", ex);
            throw new TmdbClientException(ex.getMessage());
        }
    }


    private <T> T fetchResponse(Response<T> tmdbApiResponse) throws TmdbClientException, IOException {
        // tmdb service is up but failed in respond
        if (!tmdbApiResponse.isSuccessful()) {
            log.error("TMDB API error. reason: {}", tmdbApiResponse.message());
            throw new TmdbClientException(tmdbApiResponse.message(), tmdbApiResponse.code());
        }
        T tmdbResponseBody = tmdbApiResponse.body();
        // most likely server request was ok non expected json sent
        if (tmdbResponseBody == null) {
            log.error("TMDB API returned invalid json.");
            throw new TmdbClientException("Invalid Json returned from TMDB API");
        }
        return tmdbResponseBody;
    }

}
