package com.hdcompany.hocandroid.healthy_ictu.retrofit;

import com.hdcompany.hocandroid.healthy_ictu.model.MessModel;
import com.hdcompany.hocandroid.healthy_ictu.model.UserModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiData {
    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel> dangnhap(
      @Field("taikhoan") String tk,
      @Field("matkhau") String mk
    );


    @POST("update.php")
    @FormUrlEncoded
    Observable<MessModel> updateInfo(
            @Field("id") int id,
            @Field("matkhau") String mk,
            @Field("hoten") String ht,
              @Field("diachi") String dc,
            @Field("ngaysinh") String ns,
            @Field("upload") String photo

    );


}
