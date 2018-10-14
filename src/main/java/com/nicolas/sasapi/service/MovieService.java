package com.nicolas.sasapi.service;

import com.nicolas.sasapi.domainvalue.TMDbMovie;
import com.nicolas.sasapi.exception.TmdbClientException;
import java.util.List;

public interface MovieService {

    List<TMDbMovie> findMostPopulars() throws TmdbClientException;

    List<TMDbMovie> searchByName(String name) throws TmdbClientException;

}
