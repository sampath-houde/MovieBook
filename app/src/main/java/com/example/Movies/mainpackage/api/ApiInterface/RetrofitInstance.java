package com.example.Movies.mainpackage.api.ApiInterface;

import com.example.Movies.mainpackage.api.ApiInterface.OMDBapi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit = null;
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
    }
}
