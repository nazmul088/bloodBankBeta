package com.example.blood;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserSearchApi {


    @GET(" ")
    Call<List<UserSearch>> searchUserByNameBloodDivisionDistrictAvailability(@Query("name")String name,@Query("bg") String bg,@Query("DivisionCode") String DivisionCode,
                                                                             @Query("DistrictCode") String DistrictCode,
                                                                             @Query("status") String status,@Query("page") String page);
/*
    @GET(" ")
    Call<List<UserSearch>> searchUserbyBlood(@Query("bg") int bg);

    @GET(" ")
    Call<List<UserSearch>> searchUserbyDistrict(@Query("DistrictCode") int DistrictCode);

    @GET(" ")
    Call<List<UserSearch>> searchUserbyAvailability(@Query("status") int status);

    @GET(" ")
    Call<List<UserSearch>> searchUserbyDistrictAndBlood(@Query("bg") int bg,@Query("DistrictCode") int DistrictCode);

    @GET(" ")
    Call<List<UserSearch>> searchUserbyDistrictAndAvailability(@Query("DistrictCode") int DistrictCode,@Query("status") int status);

    @GET(" ")
    Call<List<UserSearch>> searchUserbyBloodAndAvailability(@Query("bg") int bg,@Query("status") int status);

    @GET(" ")
    Call<List<UserSearch>> searchUserbyBloodDistrictAndAvailability(@Query("bg") int bg,@Query("status") int status,@Query("DistrictCode") int DistrictCode);

    @GET(" ")
    Call<List<UserSearch>> getAllUsers();

    @GET(" ")
    Call<List<UserSearch>> searchUserByName(@Query("name") String name);

    @GET(" ")
    Call<List<UserSearch>> searchUserByNameBloodDistrictAvailability(@Query("name") String name,@Query("bg") int bg,@Query("DistrictCode") int DistrictCode,@Query("status") int status);

    @GET(" ")
    Call<List<UserSearch>> searchUserByNameBloodGroupDistrict(@Query("name") String name,@Query("bg") int bg,@Query("DistrictCode") int DistrictCode);

    @GET(" ")
    Call<List<UserSearch>> searchUserByNameDistrictAvailability(@Query("name") String name,@Query("DistrictCode") int DistrictCode,@Query("status") int status);

    @GET(" ")
    Call<List<UserSearch>> searchUserByNameBloodAvailability(@Query("name") String name,@Query("bg") int bg,@Query("status") int status);

    @GET(" ")
    Call<List<UserSearch>> searchUserByNameBlood(@Query("name") String name,@Query("bg") int bg);

    @GET(" ")
    Call<List<UserSearch>> searchUserByNameDistrict(@Query("name") String name,@Query("DistrictCode") int DistrictCode);

    @GET(" ")
    Call<List<UserSearch>> searchUserByNameAvailability(@Query("name") String name,@Query("status") int status);

    @GET(" ")
    Call<List<UserSearch>> searchUserByDivision(@Query("DivisionCode") int DivisionCode);

    @GET(" ")
    Call<List<UserSearch>> searchUserByBloodGroupAndDivision(@Query("bg") int bg,@Query("DivisionCode") int DivisionCode);

    @GET(" ")
    Call<List<UserSearch>> searchUserByAvailabilityAndDivision(@Query("status") int status,@Query("DivisionCode") int DivisionCode);

    @GET(" ")
    Call<List<UserSearch>> searchUserByBloodAvailabilityAndDivision(@Query("bg") int bg,@Query("status") int status,@Query("DivisionCode") int DivisionCode);

    @GET(" ")
    Call<List<UserSearch>> searchUserByNameDivision(@Query("name") String name,@Query("DivisionCode") int DivisionCode);

    @GET(" ")
    Call<List<UserSearch>> searchUserByNameBloodGroupAndDivision(@Query("name") String name, @Query("bg") int bg,@Query("DivisionCode") int DivisionCode);

    @GET(" ")
    Call<List<UserSearch>> searchUserByNameAvailabilityAndDivision(@Query("name") String selectedName, @Query("status") int selectedAvailabilityCode,@Query("DivisionCode") int selectedDivisionCode);

    @GET(" ")
    Call<List<UserSearch>> searchUserByNameBloodAvailabilityAndDivision(@Query("name") String selectedName,@Query("bg") int selectedBloodCode,@Query("status") int selectedAvailabilityCode,@Query("DivisionCode") int selectedDivisionCode);*/

}
