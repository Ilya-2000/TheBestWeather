package com.impact.thebestweather.network

import com.impact.thebestweather.models.DailyWeather
import com.impact.thebestweather.utils.Constant
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("/data/2.5/onecall?")
    fun getOneCallWeather(
        @Query("lat")lat: Double,
        @Query("lon")lon: Double,
        @Query("exclude")exclude: String,
        @Query("appid")appid: String
    ): Deferred<DailyWeather>


    companion object {
        fun create() : WeatherApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl(Constant.BASE_URL)
                .build()
            return retrofit.create(WeatherApiService::class.java)
        }
    }
}