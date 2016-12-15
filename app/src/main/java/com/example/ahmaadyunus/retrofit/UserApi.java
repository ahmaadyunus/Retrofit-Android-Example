package com.example.ahmaadyunus.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by ahmaadyunus on 15/12/16.
 */

public interface UserApi {

    @GET("https://private-80e9a-android23.apiary-mock.com/users")
    Call<Users> getUsers();


    @GET("https://private-80e9a-android23.apiary-mock.com/users/{id}")

    Call<User> getUser(@Path("id") String user_id);


    @PUT("https://private-80e9a-android23.apiary-mock.com/users/{id}")

    Call<User> updateUser(@Path("id") int user_id, @Body User user);


    @POST("https://private-80e9a-android23.apiary-mock.com/users")

    Call<User> saveUser(@Body User user);


    @DELETE("https://private-80e9a-android23.apiary-mock.com/users/{id}")

    Call<User> deleteUser(@Path("id") String user_id);

}