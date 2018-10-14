package com.nicolas.sasapi.service;

import com.nicolas.sasapi.domainvalue.TMDbMovie;
import com.nicolas.sasapi.exception.TmdbClientException;
import com.nicolas.sasapi.service.restclient.TMDbClientService;
import com.nicolas.sasapi.service.restclient.bean.TMDbResponseBean;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Slf4j
@Service
public class DefaultMovieService implements MovieService {

    private final String apiKey;

    private final TMDbClientService tmDbClientService;

    public DefaultMovieService(@Value("${api.tmdb.key}") String apiKey, TMDbClientService tmDbClientService) {
        this.apiKey = apiKey;
        this.tmDbClientService = tmDbClientService;
    }

    @Override
    public List<TMDbMovie> findMostPopulars() throws TmdbClientException {
        try {
            Response<TMDbResponseBean> tmdbResponse = tmDbClientService.getMostPopulars(apiKey).execute();
            TMDbResponseBean tmDbResponseBean = getTmDbResponseBean(tmdbResponse);
            return tmDbResponseBean.getTmDbMovies();
        } catch (IOException ex) {
            log.error("Error when fetching most popular movies", ex);
            throw new TmdbClientException(ex.getMessage());
        }
    }

    @Override
    public List<TMDbMovie> searchByName(String name) throws TmdbClientException {
        try {
            Response<TMDbResponseBean> tmdbResponse = tmDbClientService.searchByName(name, apiKey).execute();
            TMDbResponseBean tmDbResponseBean = getTmDbResponseBean(tmdbResponse);
            return tmDbResponseBean.getTmDbMovies();
        } catch (IOException ex) {
            log.error("Error searching movie by name", ex);
            throw new TmdbClientException(ex.getMessage());
        }
    }

    private TMDbResponseBean getTmDbResponseBean(Response<TMDbResponseBean> tmdbResponse)
            throws TmdbClientException, IOException {
        // TMDb service can fail due any server reason.
        if (!tmdbResponse.isSuccessful()) {
            throw new TmdbClientException("Response was not successful: " + tmdbResponse.errorBody().string());
        }
        TMDbResponseBean tmDbResponseBean = tmdbResponse.body();
        // most likely server was right but different json was sent. Like API Key invalid.
        if (tmDbResponseBean == null) {
            throw new TmdbClientException("Response was empty. Check TMDB API Key is valid.");
        }
        return tmDbResponseBean;
    }
}
