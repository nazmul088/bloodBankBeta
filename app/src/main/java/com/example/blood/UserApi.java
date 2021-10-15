package com.example.blood;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {
    @PUT("{id}")
    Call<User> updateUser(@Path("id") String id, @Body User user,@Header("x-auth-token") String token);


    @POST("login")
    Call<User> logInUser(@Body User user);

    @POST(" ")
    Call<User> addUser(@Body User user);


    @GET("{id}")
    Call<UserSearch> getAParticularUser(@Path("id") String id);

}
