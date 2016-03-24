package com.example.enda.flickadvisor.interfaces;

import com.example.enda.flickadvisor.models.MovieReview;

import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by enda on 29/02/16.
 */
public interface MovieApiService {

    @GET(value = "movies/{id}/reviews")
    Call<RealmList<MovieReview>> getMovieReviews(@Path("id") long id);
    @POST(value = "movies/reviews/create")
    Call<MovieReview> createMovieReview(@Body MovieReview review);
    @PUT(value = "movies/reviews/update")
    Call<MovieReview> updateMovieReview(@Body MovieReview review);
}
