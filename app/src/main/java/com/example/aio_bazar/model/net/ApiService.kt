package com.example.aio_bazar.model.net

import com.example.aio_bazar.model.data.AddNewCommentResponse
import com.example.aio_bazar.model.data.AdsResponse
import com.example.aio_bazar.model.data.CommentResponse
import com.example.aio_bazar.model.data.LoginResponse
import com.example.aio_bazar.model.data.ProductResponse
import com.example.aio_bazar.model.repository.TokenInMemory
import com.example.aio_bazar.util.BASE_URL
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    //authentication

    @POST("signUp")
    suspend fun signUp(@Body jsonObject: JsonObject):LoginResponse

    @POST("signIn")
    suspend fun signIn(@Body jsonObject: JsonObject):LoginResponse

    @GET("refreshToken")
    fun refreshToken(): Call<LoginResponse>

    //product

    @GET("getProducts")
   suspend fun getAllProduct():ProductResponse

    @GET("getSliderPics")
   suspend fun getAllAds():AdsResponse

   //comment

   @POST("getComments")
   suspend fun getComments(@Body jsonObject: JsonObject):CommentResponse

   @POST("addNewComment")
   suspend fun AddNewComment(@Body jsonObject: JsonObject):AddNewCommentResponse
}

fun createApiService():ApiService{

    val okHttpClient=OkHttpClient.Builder()
        .addInterceptor {
            val oldRequest=it.request()
            val newRequest=oldRequest.newBuilder()
            if(TokenInMemory.token!=null)
                newRequest.addHeader("Authorization",TokenInMemory.token!!)

            newRequest.method(oldRequest.method,oldRequest.body)
           return@addInterceptor it.proceed(newRequest.build())
        }.build()

    val retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    return retrofit.create(ApiService::class.java)
}