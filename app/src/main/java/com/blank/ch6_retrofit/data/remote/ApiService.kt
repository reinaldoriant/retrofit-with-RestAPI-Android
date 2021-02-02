package com.blank.ch6_retrofit.data.remote

import com.blank.ch6_retrofit.data.model.*
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @GET("/api/posts")
    fun getPost(): Single<PostMsg>

    @POST("/api/v1/auth/login")
    fun login(@Body requestLogin: RequestLogin): Single<LoginMsg>

    @POST("/api/v1/auth/register")
    fun regis(@Body requestRegis: RequestRegis): Single<RegisMsg>

    @GET("/api/v1/auth/me")
    fun me(@Header("Authorization") authorization: String): Single<MeMsg>

    @Multipart
    @PUT("/api/v1/users")
    fun uploadImage(
        @Header("Authorization") authorization: String,
        @Part("username") usernameBody: RequestBody,
        @Part("email") bodyEmail: RequestBody,
        @Part multipartBody: MultipartBody.Part
    ): Single<ResponseUploadImg>
}