package com.resto.app.network;

import com.resto.app.common.MessageModel;
import com.resto.app.model.RestaurantListModel;
import com.resto.app.model.SignInModel;
import com.resto.app.model.SignUpModel;
import com.resto.app.model.UpdateModel;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Api {

    @FormUrlEncoded
    @POST("user/")
    Call<SignUpModel> SendSignUpManager(
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("user_type") String user_type,
            @Field("restraunt_id") String restraunt_id

    );

    @FormUrlEncoded
    @POST("user/")
    Call<SignUpModel> SendSignUpUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("user_type") String user_type
    );

    @Multipart
    @POST("user/login/")
    Call<SignInModel> SignInUser(
            @Part("email") RequestBody email,
            @Part("password") RequestBody password);

    @GET("user/logout/{id}/")
    Call<MessageModel> Logout(@Path("id") int Id);

    @GET("restraunts/data")
    Call<List<RestaurantListModel>> GetRestList();

    @PUT("user/{id}/")
    Call<SignUpModel> UpdateProfile(
            @Path("id") int Id,
            @Body UpdateModel body
    );

    @GET("user/{id}/")
    Call<SignUpModel> GetUserDetails(
            @Path("id") int Id
    );
}
