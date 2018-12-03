package com.example.valentino.restapitest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ActorService {

    @GET("/3/search/person")
    Call<ActorQuery> searchedActor(@Query("api_key") String api_key, @Query("language") String language,
                              @Query("query") String query, @Query("page") String page);

}
