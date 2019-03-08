package com.udacoding.hippo.networks;

import com.udacoding.hippo.fragment.dummy.ResponsePlace;
import com.udacoding.hippo.model.ResponseConstruct;
import com.udacoding.hippo.model.ResponseSignUp;
import com.udacoding.hippo.view.detail.model.ResultRoute;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PropertyInterface {


    @GET("directions/json")
    Call<ResultRoute> route(
            @Query("origin") String origin ,
            @Query("destination") String destination ,
            @Query("key") String key
    );

    @GET("places")
    Call<ResponsePlace> actionPlace();


    @FormUrlEncoded
    @POST("register")
    Call<ResponseSignUp> signUpRequest(
            @Field("username")String name ,
            @Field("email") String email ,
            @Field("password") String pasword
    );

    @GET("construct")
    Call<ResponseConstruct> actionConstruct();
}
