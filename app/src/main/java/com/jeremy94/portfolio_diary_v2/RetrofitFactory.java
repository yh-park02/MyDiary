package com.jeremy94.portfolio_diary_v2;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    private static String BASE_URL = "https://newsapi.org";

    public static RetrofitService create(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RetrofitService.class);
    }

    private static String BASE_URL2 = "http://api.openweathermap.org/data/2.5/";
    public static RetrofitService2 create2(){
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(BASE_URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit2.create(RetrofitService2.class);

    }

}
