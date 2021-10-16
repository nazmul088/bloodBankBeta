package com.example.blood.Reset_Password;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ResetPasswordApi {
    @POST("forgot")
    Call<UserReset> SendOtpResetPassowrd(@Body UserReset userReset);

    @POST("verify-otp")
    Call<UserReset> verifyOtp(@Body UserReset userReset);
}
