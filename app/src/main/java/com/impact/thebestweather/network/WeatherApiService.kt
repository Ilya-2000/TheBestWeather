package com.impact.thebestweather.network

import com.impact.thebestweather.models.DailyWeather
import kotlinx.coroutines.Deferred

interface WeatherApiService {

    fun getOneCallWeather(): Deferred<DailyWeather>
}