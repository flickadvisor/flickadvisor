package com.example.enda.flickadvisor.services.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by enda on 21/02/16.
 */
public interface TmdbMovieService {

    String API_KEY = "f8451fab6d4c97557317ad23d839fb54";

    @GET("discover/movie?api_key=" + API_KEY)
    Call<JsonObject> discoverMovies(@Query("sort_by") String sortBy);
}
