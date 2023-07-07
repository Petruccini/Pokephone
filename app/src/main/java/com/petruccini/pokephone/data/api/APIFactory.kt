package com.petruccini.pokephone.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object APIFactory {
    private var apiBaseUrl = "https://pokeapi.co"

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient().newBuilder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()

    private fun retrofit(): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(apiBaseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit().create(serviceClass)
    }
}