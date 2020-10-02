package com.example.Movies.api;
import com.example.Movies.model.MovieSearchList;
import com.example.Movies.model.MovieTrending;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OMDBapi {
    @GET("trending/all/day?api_key=b7cd3340a794e5a2f35e3abb820b497f")
    Call<MovieTrending> getTrendingMovieList();

    @GET("search/movie?api_key=b7cd3340a794e5a2f35e3abb820b497f")
    Call<MovieSearchList> getSearchMovie(@Query("query") String query);
}
