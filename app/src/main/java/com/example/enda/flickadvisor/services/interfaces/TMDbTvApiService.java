package com.example.enda.flickadvisor.services.interfaces;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by enda on 27/02/16.
 */
public interface TMDbTvApiService {

    String API_KEY = "f8451fab6d4c97557317ad23d839fb54";

    @GET("discover/tv?api_key=" + API_KEY)
    Call<JsonObject> sortBy(@Query("sort_by") String sortBy);
    @GET("discover/tv?api_key=" + API_KEY)
    Call<JsonObject> releasedBetween(@Query("primary_release_date.gte") String gte,
                                     @Query("primary_release_date.lte") String lte);
    @GET("tv/{tvShowId}?api_key=" + API_KEY)
    Call<JsonObject> getTvShowWithId(@Path("tvShowId") long tvShowId);
}
