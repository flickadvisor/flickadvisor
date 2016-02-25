package com.example.enda.flickadvisor.services.api;

import com.example.enda.flickadvisor.models.Credentials;
import com.example.enda.flickadvisor.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by enda on 17/02/16.
 */
public interface UserApiService {
    @POST("users/new")
    Call<User> createUser(@Body User user);
    @POST("users/login")
    Call<User> login(@Body Credentials credentials);
}
