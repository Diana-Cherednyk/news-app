package com.newsapp.data.remote

import com.newsapp.data.remote.model.ApiFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class NewsApi {

    fun getApiFactory(): ApiFactory = Retrofit.Builder()
        .baseUrl(ApiFactory.NEWS_API_URL)
        .addConverterFactory(
            MoshiConverterFactory.create(
            Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        ))
        .build()
        .create(ApiFactory::class.java)
}
