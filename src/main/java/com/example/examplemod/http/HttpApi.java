package com.example.examplemod.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpApi {
    /// APIのベースURL
    private String baseUrl = "https://official-joke-api.appspot.com/";
    private static final Logger LOGGER = LogManager.getLogger();

    private ApiInterface getClient() {

        Gson gson = new GsonBuilder()
                .setLenient()  // lenientモードを有効に
                .create();

        Retrofit retro = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiInterface service = retro.create(ApiInterface.class);
        return service;
    }

    public void getJoke(JokeApiCallback callback) {
        Call<JokeEntity> btc = getClient().getJoke();
        btc.enqueue(new Callback<JokeEntity>() {
            @Override
            public void onResponse(Call<JokeEntity> call, Response<JokeEntity> response) {
                if (response.isSuccessful()) {
                    LOGGER.info("APIからのレスポンス: " + response.body());
                    JokeEntity jokeEntity = response.body();
                    callback.onResponse(jokeEntity);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                LOGGER.error("APIへのリクエスト中にエラーが発生しました。", t);
                callback.onFailure();
            }
        });
    }
}
