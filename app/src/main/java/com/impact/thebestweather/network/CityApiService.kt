package com.impact.thebestweather.network

import com.impact.thebestweather.models.location.Location
import com.impact.thebestweather.utils.Constant
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CityApiService {
    @GET("/locations/v1/cities/search")
    fun searchCity(@Query("apikey")apiKey: String,
                   @Query("q")q: String,
                   @Query("language")language: String,
                   @Query("details")details: String,
                   @Query("metric")metric: String): Single<Location>


    companion object {
        fun create() : CityApiService {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(Constant.BASE_URL)
                    .build()
            return retrofit.create(CityApiService::class.java)
        }
    }
}