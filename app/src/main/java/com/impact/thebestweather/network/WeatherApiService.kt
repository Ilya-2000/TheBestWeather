package com.impact.thebestweather.network

import com.impact.thebestweather.models.weather.daily.DailyData
import com.impact.thebestweather.models.weather.hourly.HourlyData
import com.impact.thebestweather.utils.Constant
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApiService {
    @GET("/forecasts/v1/daily/5day/{id}")
    fun getDailyWeatherFromNetwork(
            @Path("id" )id: String,
            @Query("apikey")apiKey: String,
            @Query("language")language: String,
            @Query("details")details: String,
            @Query("metric")metric: String,
    ): Observable<DailyData>
    @GET("/forecasts/v1/hourly/12hour/{id}")
    fun getHourlyWeatherFromNetwork(
            @Path("id" )id: String,
            @Query("apikey")apiKey: String,
            @Query("language")language: String,
            @Query("details")details: String,
            @Query("metric")metric: String,
    ): Observable<HourlyData>


    companion object {
        fun create() : WeatherApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build()
            return retrofit.create(WeatherApiService::class.java)
        }
    }
}