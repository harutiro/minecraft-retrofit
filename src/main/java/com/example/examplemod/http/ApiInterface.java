package com.example.examplemod.http;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("jokes/random")
    Call<JokeEntity> getJoke();
}
