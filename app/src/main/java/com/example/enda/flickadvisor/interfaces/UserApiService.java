package com.example.enda.flickadvisor.interfaces;

import com.example.enda.flickadvisor.models.Credentials;
import com.example.enda.flickadvisor.models.UserTbl;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by enda on 17/02/16.
 */
public interface UserApiService {
    @POST("users/new")
    Call<UserTbl> createUser(@Body UserTbl user);
    @POST("users/login")
    Call<UserTbl> login(@Body Credentials credentials);
}
