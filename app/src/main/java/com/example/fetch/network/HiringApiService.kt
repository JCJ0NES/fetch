package com.example.fetch.network

import com.example.fetch.model.HiringData
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"
private val json = Json { coerceInputValues = true }

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    //.addConverterFactory(ScalarsConverterFactory.create())
    //.addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface HiringApiService {
    @GET("hiring.json")
    suspend fun getHiring(): List<HiringData>
}

object HiringApi {
    val retrofitService : HiringApiService by lazy {
        retrofit.create(HiringApiService::class.java)
    }
}