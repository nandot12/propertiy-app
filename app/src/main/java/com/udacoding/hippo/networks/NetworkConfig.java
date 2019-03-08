package com.udacoding.hippo.networks;

import com.udacoding.hippo.utils.Constant;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkConfig {


    private OkHttpClient getInterceptor(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)

                .build();

        return client;
    }

    private Retrofit getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getInterceptor())
                .build();

        return retrofit;

    }

    private Retrofit getRetrofitMaps(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL_MAPS)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getInterceptor())
                .build();

        return retrofit;

    }

    public PropertyInterface getService(){
        return getRetrofit().create(PropertyInterface.class);
    }

    public PropertyInterface getServiceMap(){
        return getRetrofitMaps().create(PropertyInterface.class);
    }


}
