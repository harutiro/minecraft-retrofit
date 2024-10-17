package com.example.examplemod.http;

public interface JokeApiCallback {
    void onResponse(JokeEntity jokeEntity);
    void onFailure();
}
