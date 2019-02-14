package com.example.learningapi.api;

import com.example.learningapi.model.Biodata;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("insert.php")
    Call<Biodata> saveData(
            @Field("nama") String nama,
            @Field("alamat") String alamat
    );

    @GET("select.php")
    Call<List<Biodata>> getBiodata();

    @FormUrlEncoded
    @POST("update.php")
    Call<Biodata> updateBiodata(
            @Field("id") int id,
            @Field("nama") String nama,
            @Field("alamat") String alamat
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call<Biodata> deleteBiodata(
            @Field("id") int id
    );
}
