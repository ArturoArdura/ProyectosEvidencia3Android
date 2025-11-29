package com.arturo.act9aarturo.network;

import com.arturo.act9aarturo.models.CharacterResponse;
import com.arturo.act9aarturo.models.EpisodeResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RickAndMortyApiService {
    @GET("character")
    Call<CharacterResponse> getCharacters();

    @GET("episode")
    Call<EpisodeResponse> getEpisodes();
}