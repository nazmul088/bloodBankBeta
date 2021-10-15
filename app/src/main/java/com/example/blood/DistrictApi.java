package com.example.blood;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DistrictApi {

    @GET("disAll")
    Call<List<District>> getDistricts();

}