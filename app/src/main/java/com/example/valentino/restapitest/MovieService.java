package com.example.valentino.restapitest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieService {

    @GET("t={movie}")
    Call<Movie> searchedMovie(@Path("movie") String movie);

}
