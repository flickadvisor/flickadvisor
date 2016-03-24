package com.example.enda.flickadvisor.interfaces;

import com.example.enda.flickadvisor.models.Credentials;
import com.example.enda.flickadvisor.models.UserMovie;
import com.example.enda.flickadvisor.models.UserTbl;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by enda on 17/02/16.
 */
public interface UserApiService {
    @POST("users/new")
    Call<UserTbl> createUser(@Body UserTbl user);
    @POST("users/login")
    Call<UserTbl> login(@Body Credentials credentials);
    @POST(value = "users/movies/create")
    Call<UserMovie> createUserMovie(@Body UserMovie userMovie);
    @PUT(value = "users/movies/update")
    Call<UserMovie> updateUserMovie(@Body UserMovie userMovie);
    @GET(value = "users/{userId}/movies/{movieId}")
    Call<UserMovie> getUserMovie(@Path("userId") long userId, @Path("movieId") long movieId);
}
