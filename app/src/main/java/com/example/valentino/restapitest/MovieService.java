package com.example.valentino.restapitest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    //@Headers("apikey:2dbc1f17")
    @GET("/")
    Call<Movie> searchedMovie(@Query("t") String movie, @Query("apikey") String key);

}
