package com.example.blood;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstanceUserSearch {
    private static Retrofit retrofit;
    private static final String BASEURL="http://absb.herokuapp.com/api/search/";

    public static Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
