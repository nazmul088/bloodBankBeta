package com.example.blood;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstanceAuth {

    private static Retrofit retrofit; //192.168.92.1:3000
    private static final String BASEURL="http://absb.herokuapp.com/api/auth/";

    public static Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
