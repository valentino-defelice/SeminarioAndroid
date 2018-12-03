package com.example.valentino.restapitest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

    @GET("/")
    Call<Movie> searchedMovie(@Query("t") String movie, @Query("apikey") String key);

}
