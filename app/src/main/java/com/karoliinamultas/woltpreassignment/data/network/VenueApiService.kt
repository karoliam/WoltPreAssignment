package com.karoliinamultas.woltpreassignment.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.karoliinamultas.woltpreassignment.data.model.VenueData
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


interface VenueApiService {
    @GET("v1/pages/restaurants")
    suspend fun getVenues(@Query("lat") latitudeParam: String, @Query("lon") longitudeParam: String): VenueData
}


object VenueApi {
    val retrofitService : VenueApiService by lazy {
        retrofit.create(VenueApiService::class.java)
    }
}
