package com.example.enda.flickadvisor.services.interfaces;

import com.example.enda.flickadvisor.models.MovieReview;
import com.example.enda.flickadvisor.models.UserMovie;

import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by enda on 29/02/16.
 */
public interface MovieApiService {

    @GET(value = "movies/{id}/reviews")
    Call<RealmList<MovieReview>> getMovieReviews(@Path("id") long id);
    @GET(value = "movies/{movieId}/user/{userId}")
    Call<UserMovie> getUserMovie(@Path("movieId") long movieId, @Path("userId") long userId);
}
