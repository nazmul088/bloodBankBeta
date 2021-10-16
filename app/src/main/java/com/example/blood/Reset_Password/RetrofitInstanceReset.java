package com.example.blood.Reset_Password;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstanceReset {

    private static Retrofit retrofit; //192.168.92.1:3000
    private static final String BASEURL="http://absb.herokuapp.com/api/user/";

    public static Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
