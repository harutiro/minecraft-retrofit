package com.example.examplemod.http;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("jokes/random")
    Call<JokeEntity> getJokeRandom();

    @GET("jokes/{id}")
    Call<JokeEntity> getJoke(@Path("id") int id);
}
