package com.example.blood.Hospital;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HospitalApi {

    @GET("available-hospitals")
    Call<Root> searchByDistrict(@Query("search") String districts);

    @GET("available-hospitals")
    Call<Root> searchByDistrictPage(@Query("search") String districts,@Query("page") int page);

    @GET("available")
    Call<HospitalActivity.Available_beds> getBedsAvailable();

    @GET("available-hospitals")
    Call<Root> searchByMultipleDistrictsAndTypes(@Query("districts") String districts,@Query("page") int page,@Query("type") String type ,@Query("order_by") String order);
}
