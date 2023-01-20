package com.example.masir.services.http

import androidx.databinding.ktx.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {

    private val BASE_URL = "https://api.github.com/"

    private fun logger(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) interceptor.level = HttpLoggingInterceptor.Level.BODY
        else interceptor.level = HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logger())
        .addInterceptor(Interceptor {
            val oldRequest = it.request()
            val newRequestBuilder = oldRequest.newBuilder()
            newRequestBuilder.addHeader("Accept" , "application/vnd.github+json")
            return@Interceptor it.proceed(newRequestBuilder.build())
        }).build()


    val api : Api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(Api::class.java)
    }
}