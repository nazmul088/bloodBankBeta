package com.example.blood.Cylinder;

import com.example.blood.Ambulance;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CylinderApi {


    @GET("search/cylinder")
    Call<List<Cylinder>> getCylinderByNameDivisionDistrict(@Query("text") String text,@Query("DivisionCode") String DivisionCode,
                                                           @Query("DistrictCode") String DistrictCode,@Query("page") String page);

/*
    @GET("cylinder")
    Call<List<Cylinder>> getAllCylinder();

    @GET("search/cylinder")
    Call<List<Cylinder>> searchByDistrict(@Query("DistrictCode") int DistrictCode);

    @GET("search/cylinder")
    Call<List<Cylinder>> searchByDivision(@Query("DivisionCode") int DivisionCode);

    @GET("search/cylinder")
    Call<List<Cylinder>> searchByNameDivision(@Query("text") String Name,@Query("DivisionCode") int DivisionCode);

    @GET("search/cylinder")
    Call<List<Cylinder>> searchByNameDistrict(@Query("text") String Name, @Query("DistrictCode") int DistrictCode);

    @GET("search/cylinder")
    Call<List<Cylinder>> searchByName(@Query("text") String Name);*/
}
