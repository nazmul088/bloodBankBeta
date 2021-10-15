package com.example.blood;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AmbulanceApi {


    @GET("search/ambulance")
    Call<List<Ambulance>> getAmbulanceByNameDivisionDistrict(@Query("text") String text,@Query("DivisionCode") String DivisionCode,@Query("DistrictCode") String DistrictCode
                                                            ,@Query("page") String page);

    @GET("ambulance")
    Call<List<Ambulance>> getAllAmbulance();

    @GET("search/ambulance")
    Call<List<Ambulance>> searchByDistrict(@Query("DistrictCode") int DistrictCode);

    @GET("search/ambulance")
    Call<List<Ambulance>> searchByDivision(@Query("DivisionCode") int DivisionCode);

    @GET("search/ambulance")
    Call<List<Ambulance>> searchByNameDivision(@Query("text") String Name,@Query("DivisionCode") int DivisionCode);

    @GET("search/ambulance")
    Call<List<Ambulance>> searchByNameDistrict(@Query("text") String Name, @Query("DistrictCode") int DistrictCode);

    @GET("search/ambulance")
    Call<List<Ambulance>> searchByName(@Query("text") String Name);
}

