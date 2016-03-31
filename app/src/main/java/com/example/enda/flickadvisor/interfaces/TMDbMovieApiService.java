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

    String API_KEY = "f8451fab6d4c97557317ad23d839fb54";

    @GET("discover/movie?api_key=" + API_KEY)
    Call<JsonObject> sortBy(@Query("sort_by") String sortBy);
    @GET("discover/movie?api_key=" + API_KEY)
    Call<JsonObject> releasedBetween(@Query("language") String language,
                                     @Query("primary_release_date.gte") String gte,
                                     @Query("primary_release_date.lte") String lte);
    @GET("movie/{movieId}?api_key=" + API_KEY)
    Call<JsonObject> getMovieWithId(@Path("movieId") long movieId, @Query("language") String language);
//    https://api.themoviedb.org/3/discover/movie?with_genres=35&vote_average.gte=7&sort_by=vote_average&vote_count.gte=100&api_key=f8451fab6d4c97557317ad23d839fb54
    @GET("discover/movie?api_key=" + API_KEY)
    Call<JsonObject> discoverMovies(@Query("with_genres") long[] genres,
                                    @Query("language") String language,
                                    @Query("vote_average.gte") int minimumVoteAverage,
                                    @Query("sort_by") String sortBy,
                                    @Query("vote_count.gte") int minimumVoteCount);
}
