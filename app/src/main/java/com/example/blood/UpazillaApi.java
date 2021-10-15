package com.example.blood;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UpazillaApi {
    @GET("upzAll")
    Call<List<Upazilla>> getUpazillas();
}