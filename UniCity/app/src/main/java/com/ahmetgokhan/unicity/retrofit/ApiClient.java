package com.ahmetgokhan.unicity.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String main_url = "http://94.54.213.189:5000/";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(main_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
