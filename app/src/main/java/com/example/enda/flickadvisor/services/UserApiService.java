package com.example.enda.flickadvisor.services;

import com.example.enda.flickadvisor.models.User;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.Call;

/**
 * Created by enda on 17/02/16.
 */
public interface UserApiService {
    @POST("users/new")
    Call<User> createUser(@Body User user);
    @POST("users/login")
    Call<User> login(String username, String password);
    @GET("users/get")
    Call<User> getUser();
}
