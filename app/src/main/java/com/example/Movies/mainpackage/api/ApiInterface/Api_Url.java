package com.example.Movies.mainpackage.api.ApiInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api_Url {

    public static final String TRENDING_URL = "https://api.themoviedb.org/3/trending/all/day?api_key=b7cd3340a794e5a2f35e3abb820b497f";
    public static final String SEARCH_URL = "https://api.themoviedb.org/3/search/movie?api_key=b7cd3340a794e5a2f35e3abb820b497f&query=";

}
/*private static Retrofit retrofit = null;
    public static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static OMDBapi getService() {

        if(retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(OMDBapi.class);
    }*/