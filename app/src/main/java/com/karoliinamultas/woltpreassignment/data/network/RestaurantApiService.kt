package com.karoliinamultas.woltpreassignment.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.karoliinamultas.woltpreassignment.data.model.RestaurantData
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.Retrofit
import retrofit2.http.GET
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.http.Query


private const val BASE_URL = "https://restaurant-api.wolt.com/"

private val json = Json {
    isLenient = true
    ignoreUnknownKeys = true
}

@OptIn(ExperimentalSerializationApi::class)
val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .build()


interface RestaurantApiService {

    @GET("v1/pages/restaurants")
    suspend fun getRestaurants(@Query("lat") latitudeParam: String, @Query("lon") longitudeParam: String): RestaurantData
}


object RestaurantApi {
    val retrofitService : RestaurantApiService by lazy {
        retrofit.create(RestaurantApiService::class.java)
    }
}
