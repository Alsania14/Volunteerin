package id.alin_gotama.volunteer.services;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import id.alin_gotama.volunteer.Model.ServerDefaultRespon;
import id.alin_gotama.volunteer.Model.ServerLoginModel;
import id.alin_gotama.volunteer.Model.ServerRegisterRespon;
import id.alin_gotama.volunteer.Model.ServerRespon;
import id.alin_gotama.volunteer.SQLModel.Event;
import id.alin_gotama.volunteer.SQLModel.RequestForJoinRespon;
import id.alin_gotama.volunteer.SQLModel.User;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface Services {

    @FormUrlEncoded
    @POST("/api/login")
    Call<ServerLoginModel> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/api/register")
    Call<ServerRegisterRespon> register(
            @Field("full_name") String full_name,
            @Field("username") String username,
            @Field("no_telp") String no_telp,
            @Field("bio") String bio,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/api/event/read")
    Call<ArrayList<Event>> readEvent(
            @Field("user_id") String user_id
    );

    @Multipart
    @POST("/api/event/create")
    Call<ServerDefaultRespon> createEvent(
            @Part("user_id") RequestBody user_id,
            @Part("event_name") RequestBody event_name,
            @Part("description") RequestBody description,
            @Part("tanggal_mulai") RequestBody tanggal_mulai,
            @Part("tanggal_selesai") RequestBody tanggal_selesai,
            @Part("status") RequestBody status,
            @Part("maximal_member") RequestBody  maximal_member,
            @Part MultipartBody.Part image
            );

    @FormUrlEncoded
    @POST("/api/detailevent/join")
    Call<ServerDefaultRespon> joinEvent(
            @Field("event_id") String event_id,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("/api/event/myevent")
    Call<ArrayList<Event>> readMyEvent(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("/api/event/delete")
    Call<ServerDefaultRespon> deleteEvent(
            @Field("event_id") String event_id
    );

    @FormUrlEncoded
    @POST("/api/detailevent/read")
    Call<ArrayList<User>> requestForJoin(
            @Field("event_id") String event_id
    );

}
