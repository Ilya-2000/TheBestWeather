package com.impact.thebestweather.data

import com.impact.thebestweather.models.WeatherRequest
import com.impact.thebestweather.network.WeatherApiService

class WeatherSource {
    private val TAG = "WeatherSource"
    private val weatherApiService by lazy {
        WeatherApiService.create()
    }

    suspend fun getWeather(weatherRequest: WeatherRequest) {
        weatherApiService.getOneCallWeather(weatherRequest.lat, weatherRequest.lon,
            weatherRequest.exclude, weatherRequest.appid)
    }
}