package com.example.enda.flickadvisor.interfaces;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by enda on 21/02/16.
 */
public interface TMDbMovieApiService {

//    API_BASE_URL = "https://api.themoviedb.org/3/";

    String API_KEY = "f8451fab6d4c97557317ad23d839fb54";

    @GET("discover/movie?api_key=" + API_KEY)
    Call<JsonObject> sortBy(@Query("sort_by") String sortBy);
    @GET("discover/movie?api_key=" + API_KEY)
    Call<JsonObject> releasedBetween(@Query("primary_release_date.gte") String gte,
                                     @Query("primary_release_date.lte") String lte);
    @GET("movie/{movieId}?api_key=" + API_KEY)
    Call<JsonObject> getMovieWithId(@Path("movieId") long movieId);
}
