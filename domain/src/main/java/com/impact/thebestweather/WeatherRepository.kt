package com.impact.thebestweather

import com.impact.thebestweather.models.weather.Weather
import com.impact.thebestweather.models.weather.WeatherRequestData
import io.reactivex.Single

interface WeatherRepository {
    fun getWeather(weatherRequestData: WeatherRequestData): Single<Weather>
}